/* pipe-example-1.c */

#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/mman.h>

enum
{
    MSG_STRING = 1,
    MSG_END
};

enum
{
    MESSAGE_MAGIC = 0xAFAF, // magic signature value
    MAX_MESSAGE_LEN = 4096  // maximum message length
};

struct __attribute__((packed)) message_header
{
    uint16_t magic;     // magic signature
    uint16_t type;      // type of the message
    uint16_t data_len;  // length of data
};

enum
{
    // maximum data length
    MAX_MESSAGE_DATA_LEN = MAX_MESSAGE_LEN - sizeof(struct message_header)
};

struct __attribute__((packed)) message
{
    struct message_header header;
    // payload
    uint8_t data[MAX_MESSAGE_DATA_LEN];
};

struct message simple_message(uint16_t type)
{
    return (struct message) {(struct message_header) {MESSAGE_MAGIC, type, 0}, 0};
}

struct message string_message(const char *str)
{
    struct message msg = (struct message) {(struct message_header) {MESSAGE_MAGIC, MSG_STRING, strlen(str + 1)}, 0};
    strcpy(msg.data, str);
    return msg;
}

int send(int fd, const struct message *msg)
{
    /* Check if the input data is not empty */
    if (fd < 0 || msg == NULL)
        return -1;

    /* Calculate the message size to send */
    int msg_size = sizeof(struct message_header) + msg->header.data_len;

    /* Check if message payload size is valid */
    if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
        return -1;

    /* Write data to the output pipe (we assume it is ready) */
    if (write(fd, msg, msg_size) != msg_size)
        return -2;

    return 0;
}

int receive(int fd, struct message *msg)
{
    /* Check if the input data is not empty */
    if (fd < 0 || msg == NULL)
        return -1;

    /* Try to read header */
    int msg_size = read(fd, &msg->header, sizeof(struct message_header));
    if (msg_size == 0)
        return 0;

    /* Check header magic */
    if (msg->header.magic != MESSAGE_MAGIC)
        return -2;

    /* Check if message has payload */
    if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
        return -2;
    if (msg->header.data_len > 0)
        msg_size += read(fd, &msg->data, msg->header.data_len);

    /* Return number of bytes read */
    return msg_size;
}

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}

int task2() {
    int* shmem = create_shared_memory(sizeof (int) * 10);

    for(size_t i = 0; i < 10; i++){
        shmem[i] = i + 1;
    }

    // Создадим два конвейера
    int pipes[2][2];
    pipe(pipes[0]);
    pipe(pipes[1]);

    // Создадим дочерний процесс
    pid_t pid = fork();
    if (pid == 0) {
        // Сохраним нужные дескпиторы конвейеров
        int to_parent_pipe = pipes[1][1];
        int from_parent_pipe = pipes[0][0];

        // И закроем ненужные
        close(pipes[1][0]);
        close(pipes[0][1]);

        // Будем читать строки и отправлять их сообщениями
        char str[MAX_MESSAGE_DATA_LEN];
        for (;;)
        {
            size_t i;
            int index, value;
            scanf("%zu %d", &index, &value);
            if(index < 0 || index > 9) break;
            i = (size_t) index;
            shmem[i] = value;

//            // Прочитаем строку
//            if (fgets(str, MAX_MESSAGE_DATA_LEN, stdin) == NULL)
//                return 0;
//            if (strlen(str) <= 1)
//                break;

            // И отпавим её родителю
            struct message msg = string_message("Ok");
            send(to_parent_pipe, &msg);
        }

        // Отправим сообщение о завершении ввода
        struct message msg = simple_message(MSG_END);
        send(to_parent_pipe, &msg);

        // Закроем дескпиторы
        close(to_parent_pipe);
        close(from_parent_pipe);

        return 0;
    }

    // Далее выполняется только родительский поток
    // Сохраним нужные дескпиторы конвейеров
    int from_child_pipe = pipes[1][0];
    int to_child_pipe = pipes[0][1];

    // И закроем ненужные
    close(pipes[1][1]);
    close(pipes[0][0]);

    // Будем ждать, пока ребенок не пришлет сообщение
    struct message msg;
    while (1)
    {
        int ret;
        while (ret = receive(from_child_pipe, &msg) == 0);
        if (ret < 0)
            return 1;

        // Если это строка - выведем её
        if (msg.header.type == MSG_STRING) {
            for(int i = 0; i < 10; i++){
                printf("%d ", shmem[i]);
            }
            printf("\nRecieved string from child: %s\n", (char *) msg.data);
        }
            // Если конец ввода - прервемся
        else if (msg.header.type == MSG_END)
        {
            printf("Recieved END from child\n");
            break;
        }
    }

    // Дождемся завершения ребенка
    waitpid(pid, NULL, 0);

    // Закроем дескпиторы
    close(from_child_pipe);
    close(to_child_pipe);

    return 0;
}
