import 'package:flutter/material.dart';
import 'package:get/get_utils/get_utils.dart';
import 'package:lab8/data/position.dart';
import 'package:lab8/data/worker.dart';
import 'package:lab8/data/workers.dart';
import 'package:lab8/utilities/addWorker.dart';
import 'package:lab8/utilities/utils.dart';

import '../data/status.dart';
import '../utilities/scrollableWidget.dart';
import '../utilities/text_dialog_widget.dart';

class DataTablePage extends StatefulWidget {
  final String login;

  const DataTablePage(this.login, {Key? key}) : super(key: key);

  @override
  State<DataTablePage> createState() => _DataTablePageState(login);
}

class _DataTablePageState extends State<DataTablePage> {
  final String login;
  _DataTablePageState(this.login);

  late List<Worker> workers;
  @override
  void initState() {
    super.initState();
    workers = List.of(allWorkers);
  }

  @override
  Widget build(BuildContext context) => Scaffold(
    body: Column(
      children: [
        ScrollableWidget(
          child: buildDataTable(),
        ),
        Padding(padding: EdgeInsets.all(12.0)),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                height: 50,
                decoration: BoxDecoration(
                    color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                child: FlatButton(
                  onPressed: () => addWorker(),
                  child: Text(
                    'add'.tr,
                    style: const TextStyle(color: Colors.white, fontSize: 25),
                  ),
                ),
              ),
            ),
          ],
        ),
      ],
    ),
  );

  Widget buildDataTable() {
    final columns = ['id','name','cord_x','cord_y','creationDate','salary','position','status','birthday','height','passportID','author'];
    return DataTable(
        columns: getColumns(columns), rows: getRows(workers),
    );
  }

  List<DataColumn> getColumns(List<String> columns) {
    return columns.map((String column) {
      return DataColumn(label: Text(column));
    }).toList();
  }

  List<DataRow> getRows(List<Worker> workers) => workers.map((Worker worker) {
    final cells = [
      worker.id, worker.name, worker.coordinates.x, worker.coordinates.y,
      worker.creationDate, worker.salary, worker.position.toString(),
      worker.status.toString(), worker.person.birthday, worker.person.height,
      worker.person.passportID, worker.author
    ];
    return DataRow(
      cells: Utils.modelBuilder(cells, (index, cell) {
        bool showEditIcon = (index == 1 || index == 2 || index == 3 || index == 5 || index == 6 || index == 7 || index == 9 || index == 10)
        && worker.author == login;

        return DataCell(
          Text('$cell'),
          showEditIcon: showEditIcon,
          onTap: () {
            if(showEditIcon){
              switch (index) {
                case 1:
                  editName(worker);
                  break;
                case 2:
                  editCordX(worker);
                  break;
                case 3:
                  editCordY(worker);
                  break;
                case 5:
                  editSalary(worker);
                  break;
                case 6:
                  editPosition(worker);
                  break;
                case 7:
                  editStatus(worker);
                  break;
                case 9:
                  editHeight(worker);
                  break;
                case 10:
                  editPassportId(worker);
                  break;
              }
            }
          },
        );
      }),
    );
  }).toList();

  Future addWorker() async {
    final AddWorker = await showAddDialog(
      context,
      workers: workers,
      username: login
    );

    setState(() => workers.add(AddWorker));
  }

  Future editName(Worker editWorker) async {
    final Name = await showTextDialog(
      context,
      title: '${'update'.tr} Name',
      value: editWorker.name,
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.name = Name;
      return worker;
    }).toList());
  }

  Future editCordX(Worker editWorker) async {
    final X = await showTextDialog(
      context,
      title: '${'update'.tr} Cord X',
      value: editWorker.coordinates.x.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.coordinates.x = int.parse(X);
      return worker;
    }).toList());
  }

  Future editCordY(Worker editWorker) async {
    final Y = await showTextDialog(
      context,
      title: '${'update'.tr} Cord Y',
      value: editWorker.coordinates.y.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.coordinates.y = double.parse(Y);
      return worker;
    }).toList());
  }

  Future editSalary(Worker editWorker) async {
    final Salary = await showTextDialog(
      context,
      title: '${'update'.tr} Salary',
      value: editWorker.salary.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.salary = int.parse(Salary);
      return worker;
    }).toList());
  }

  Future editPosition(Worker editWorker) async {
    final Pos = await showTextDialog(
      context,
      title: '${'update'.tr} Position',
      value: editWorker.position.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.position = Position.values.firstWhere((e) => e.toString() == Pos);
      return worker;
    }).toList());
  }

  Future editStatus(Worker editWorker) async {
    final Stat = await showTextDialog(
      context,
      title: '${'update'.tr} Status',
      value: editWorker.status.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.status = Status.values.firstWhere((e) => e.toString() == Stat);
      return worker;
    }).toList());
  }

  Future editHeight(Worker editWorker) async {
    final Height = await showTextDialog(
      context,
      title: '${'update'.tr} Height',
      value: editWorker.person.height.toString(),
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.person.height = double.parse(Height);
      return worker;
    }).toList());
  }

  Future editPassportId(Worker editWorker) async {
    final PassportId = await showTextDialog(
      context,
      title: '${'update'.tr} Passport Id',
      value: editWorker.person.passportID,
    );

    setState(() => workers = workers.map((worker) {
      final isEditedWorker = worker == editWorker;
      if(isEditedWorker) worker.person.passportID = PassportId;
      return worker;
    }).toList());
  }

}
