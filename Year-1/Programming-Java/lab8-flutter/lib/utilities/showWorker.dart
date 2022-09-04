import 'package:flutter/material.dart';
import 'package:lab8/data/worker.dart';

Future<T?> showWorkerDialog<T>(
    BuildContext context, {
      required Worker worker,
    }) =>
    showDialog<T>(
      context: context,
      builder: (context) => ShowWorker(
        worker: worker,
      ),
    );


class ShowWorker extends StatefulWidget {
  final Worker worker;
  const ShowWorker({Key? key, required this.worker}) : super(key: key);

  @override
  State<ShowWorker> createState() => _ShowWorkerState(worker);
}

class _ShowWorkerState extends State<ShowWorker> {
  late Worker worker;
  _ShowWorkerState(this.worker);
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text("Worker"),
      content: Column(
        children: [
          Text('ID: ${worker.id}'),
          Text('Name: ${worker.name}'),
          Text('Cord X: ${worker.coordinates.x}'),
          Text('Cord Y: ${worker.coordinates.y}'),
          Text('Salary: ${worker.salary}'),
          Text(worker.position.toString()),
          Text(worker.status.toString()),
          Text('Birthday: ${worker.person.birthday}'),
          Text('Height: ${worker.person.height}'),
          Text('Passport ID: ${worker.person.passportID}'),
          Text('Author: ${worker.author}')
        ],
      ),
    );
  }
}
