# Assignment: PE file reader
---
Лабораторная работа: Чтение PE файла

# Подготовка

- Прочитайте документацию по формату PE файла: https://learn.microsoft.com/en-us/windows/win32/debug/pe-format
- Ознакомьтесь с документацией к Doxygen: https://www.doxygen.nl/manual/

# PE файл

PE файлы используются в ОС Windows для хранения объектных и исполняемых файлов. 
Для "совместимости" со старыми операционными системами PE файл начинается с корректной программы для MS-DOS, которая выводит сообщение "This program cannot be run in DOS mode." Поэтому заголовок и данные в PE файле смещены от начала файла и эффективное смещение записано со смещением `0x3c` от начала файла.

По данному смещению записано magic value `PE\0\0', за которым следует заголовок и данные.

Для чтения заголовков удобно использовать упакованные структуры.  

## Упаковка данных

Для упаковки стркутур следует ипользовать `#pragma packed(...)` для компилятора Visual C и `__attribute__((packed))` для компиляторов gcc и clang, которые используются при автоматическом тестировании. Используя директивы препорцессора можно выяснить вид компилятора и написать портабельный код:

```c
#ifdef __clang__
    printf("Hello world from clang!\n");
#elif defined __GNUC__
  printf("Hello world from gcc!\n");
#elif defined _MSC_VER
  printf("Hello world from Visual C!\n");
#else
  printf("Hello world from somethig else!\n");
#endif
```

## Смещения

Обратите внимание на смещения в файле, некоторые из них абсолютные, а некоторые относительные. Можно вычислить и сохранить все абсолютные смещения для удобства работы с файлом:

```c
/// Structure containing PE file data
struct PEFile
{
  /// @name Offsets within file
  ///@{

  /// Offset to a file magic
  uint32_t magic_offset;
  /// Offset to a main PE header
  uint32_t header_offset;
  /// Offset to an optional header
  uint32_t optional_header_offset;
  /// Offset to a section table
  uint32_t section_header_offset;
  ///@}
  
  /// @name File headers
  ///@{
  
  /// File magic
  uint32_t magic;
  /// Main header
  struct PEHeader header;
  /// Optional header
  struct OptionalHeader optional_header;
  /// Array of section headers with the size of header.number_of_sections
  struct SectionHeader *section_headers;
  ///@}

  ...
};
```

# Задание

- Необходимо реализовать чтение PE файла и вывод одной его секции в файл. Формат использования такой:

```
./section-extracter <source-pe-file> <section-name> <output-bin-image>
```

- Код размещается в директории `solution/src`, заголовочные файлы ищутся в `solution/include`.
- Код должен быть портабельный и корректно работать с компиляторами gcc, clang, visual C.
- Код должен быть документирован для автоматической генерации документации в формате `doxygen`.

## Система сборки и тестирования

Для сборки кода вам предоставлена система сборки на языке CMake, самим писать систему сборки не требуется.

- В зависимости от платформы и компилятора, система сборки поддерживает несколько конфигураций с динамическими
  анализаторами (санитайзерами). Санитайзеры могут дать подробную информацию о возможных и реальных ошибках в
  программе вместо классического сообщения о segmentation fault. Выбрать подходящую конфигурацию вы можете с
  помощью переменной `CMAKE_BUILD_TYPE`:

  - `ASan` &mdash; [AddressSanitizer](https://clang.llvm.org/docs/AddressSanitizer.html),
    набор проверок на некорректное использование адресов памяти. Примеры:
    use-after-free, double-free, выход за пределы стека, кучи или статического блока.

  - `LSan` &mdash; [LeakSanitizer](https://clang.llvm.org/docs/LeakSanitizer.html),
    проверки на утечки памяти.

  - `MSan` &mdash; [MemorySanitizer](https://clang.llvm.org/docs/MemorySanitizer.html),
    проверяет, что любая используемая ячейка памяти проинициализирована на момент чтения из нее.

  - `UBSan` &mdash; [UndefinedBehaviourSanitizer](https://clang.llvm.org/docs/UndefinedBehaviorSanitizer.html),
    набор базовых проверок на неопределенное поведение. Примеры: переполнение численного типа,
    null-pointer dereference.

- Если в вашей системе имеется статический анализатор `clang-tidy`, он будет запущен во время компиляции программы.
  Список проверок описан в файле `clang-tidy-checks.txt`. Вы можете добавить свои проверки в конец этого файла.

- Директория `tester` содержит код и изображения для тестирования вашей программы. Для запуска тестов используется CTest.

- Поддержана интеграция системы сборки со средами разработки CLion, Visual Studio и Visual Studio Code.

Чтобы система сборки работала на вашей системе, вам необходимо:

### Linux и MacOS

- Компилятор (`gcc`/`clang`) и `cmake` (проверьте, что `cmake` версии 3.12 или выше)
- Если вы хотите использовать санитайзеры с GCC, установите `libasan`, `liblsan` и `libubsan` с помощью пакетного менеджера (названия могут отличаться).
- Если вы хотите использовать санитайзеры с Clang, на некоторых системах вам может понадобиться пакет `compiler-rt`.
- Если вы хотите пользоваться `clang-tidy`, установите `clang-tools-extra`.

### Windows

- Какая-либо среда разработки (CLion, Visual Studio, Visual Studio Code)
- Если вы хотите пользоваться `clang-tidy`, скачайте LLVM: https://github.com/llvm/llvm-project/releases (найдите установщик win64 под одной из версий)
- Для VS Code требуется отдельно поставить Visual Studio (с сайта Microsoft) и CMake: https://cmake.org/download/

### Инструкции по сборке и тестированию

- [Работа с терминалом](docs/Terminal.md)
- [CLion](docs/CLion.md)
- [Visual Studio](docs/VisualStudio.md)
- [Visual Studio Code](docs/VSCode.md)
