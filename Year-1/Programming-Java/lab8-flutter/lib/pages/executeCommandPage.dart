import 'package:flutter/material.dart';
import 'package:get/get_utils/get_utils.dart';

class ExecuteCommandPage extends StatefulWidget {
  const ExecuteCommandPage({Key? key}) : super(key: key);

  @override
  State<ExecuteCommandPage> createState() => _ExecuteCommandPageState();
}

class _ExecuteCommandPageState extends State<ExecuteCommandPage> {
  TextEditingController commandController = TextEditingController();
  TextEditingController resController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10.0),
            child: Text('executeTitle'.tr, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 25)),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              controller: commandController,
              decoration: InputDecoration(
                  border: const OutlineInputBorder(),
                  labelText: ('commandButton'.tr)
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Container(
              height: 50,
              decoration: BoxDecoration(
                  color: Colors.blue, borderRadius: BorderRadius.circular(20)),
              child: FlatButton(
                onPressed: () => {
                  onTapCommand()
                },
                child: Text(
                  'commandButton'.tr,
                  style: const TextStyle(color: Colors.white, fontSize: 25),
                ),
              ),
            ),
          ),
          IntrinsicHeight(
            child: TextField(
              controller: resController,
              keyboardType: TextInputType.multiline,
              maxLines: null,
              decoration: InputDecoration(
                  border: OutlineInputBorder()
              ),
            ),
          )
        ],
      ),
    );
  }

  void onTapCommand(){
    commandController.clear();
    setState(() {
      resController.text += """help : вывести справку по доступным командам
info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
insert null {element} : добавить новый элемент с заданным ключом=id
update id {element} : обновить значение элемента коллекции, id которого равен заданному
remove_key null : удалить элемент из коллекции по его ключу=id
clear : очистить коллекцию
execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
exit : завершить программу (без сохранения в файл)
remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
remove_greater_key null : удалить из коллекции все элементы, ключ=id которых превышает заданный
max_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является максимальным
print_descending : вывести элементы коллекции в порядке убывания
print_field_descending_position : вывести значения поля position всех элементов в порядке убывания
auth : выполнить авторизацию
reg : выполнить регистацию
""";
    });
  }
}
