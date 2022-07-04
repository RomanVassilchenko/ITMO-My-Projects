import 'dart:math';

import 'package:flutter/material.dart';
import 'package:get/get_utils/get_utils.dart';
import 'package:lab8/data/workers.dart';

import '../data/position.dart';
import '../data/status.dart';
import '../data/worker.dart';

Future<T?> showAddDialog<T>(
    BuildContext context, {
      required List<Worker> workers,
      required String username
    }) =>
    showDialog<T>(
      context: context,
      builder: (context) => TextAddWidget(
          value: workers,
          login: username
      ),
    );

class TextAddWidget extends StatefulWidget {
  final List<Worker> value;
  final String login;

  TextAddWidget({
    Key? key,
    required this.value, required this.login
  }) : super(key: key);

  @override
  _TextAddWidgetState createState() => _TextAddWidgetState(login);
}

class _TextAddWidgetState extends State<TextAddWidget> {
  final String login;
  _TextAddWidgetState(this.login);
  int newId = 0;
  late TextEditingController nameController;
  late TextEditingController xController;
  late TextEditingController yController;
  late TextEditingController salaryController;
  late TextEditingController positionController;
  late TextEditingController statusController;
  late TextEditingController heightController;
  late TextEditingController passportIdController;

  @override
  void initState() {
    super.initState();

    nameController = TextEditingController();
    xController = TextEditingController();
    yController = TextEditingController();
    salaryController = TextEditingController();
    positionController = TextEditingController();
    statusController = TextEditingController();
    heightController = TextEditingController();
    passportIdController = TextEditingController();

  }

  @override
  Widget build(BuildContext context) => AlertDialog(
    title: Text('newValue'.tr),
    content: Column(
      children: [
        TextField(
          controller: nameController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
            labelText: 'Name'
          ),
        ),
        TextField(
          controller: xController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Coordinate X'
          ),
        ),
        TextField(
          controller: yController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Coordinate Y'
          ),
        ),
        TextField(
          controller: salaryController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Salary'
          ),
        ),
        TextField(
          controller: positionController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Position'
          ),
        ),
        TextField(
          controller: statusController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Status'
          ),
        ),
        TextField(
          controller: heightController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Height'
          ),
        ),
        TextField(
          controller: passportIdController,
          decoration: const InputDecoration(
            border: OutlineInputBorder(),
              labelText: 'Passport Id'
          ),
        )
      ],
    ),
    actions: [
      ElevatedButton(
          child: Text('add'.tr),
          onPressed: () => _addNewValue()
      )
    ],
  );

  void _addNewValue() {
    for(int i = 0; i < widget.value.length; i++) {newId = max(newId, widget.value[i].id);}
    newId++;
    Position position = Position.DIRECTOR;
    for (var i in Position.values){
      if(i == positionController.text) position = i;
    }
    Status status = Status.RECOMMENDED_FOR_PROMOTION;
    for (var i in Status.values){
      if(i == statusController.text) status = i;
    }
    Navigator.of(context).pop(
      Worker(
          newId,nameController.text,
          Coordinates(int.parse(xController.text), double.parse(yController.text)),
          DateTime.now(),
          int.parse(salaryController.text),
          position,
          status,
          Person(
              DateTime.utc(2018 - DateTime.now().month, 01, 03),
              double.parse(heightController.text),
              passportIdController.text),
          login
      ),
    );
  }
}