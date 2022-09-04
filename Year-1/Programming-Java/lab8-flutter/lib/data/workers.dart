import 'package:lab8/data/position.dart';
import 'package:lab8/data/status.dart';
import 'package:lab8/data/worker.dart';

List<Worker> allWorkers = <Worker>[
  Worker(
      1,'Турист', Coordinates(-5,12.0),DateTime.now(), 123000, Position.LABORER,
      Status.RECOMMENDED_FOR_PROMOTION,Person(DateTime.utc(1989, 11, 9),
      189,'8737bv'),'user5'
  ),

  Worker(
      2,'Александр', Coordinates(44,-5.99),DateTime.utc(2022,01,01), 412000, Position.DIRECTOR,
      Status.HIRED,Person(DateTime.utc(2003, 10, 31),
      195,'010000'),'rossilman'
  ),

  Worker(
      4,'Eugene', Coordinates(31,38.432),DateTime.utc(2022,02,24), 5000, Position.MANAGER_OF_CLEANING,
      Status.PROBATION,Person(DateTime.utc(2001, 01, 10),
      171,'fvrjvh32'),'user3'
  ),
  Worker(
      5,'Пользователь №3', Coordinates(1231,-34234.232),DateTime.now(), 12720, Position.DIRECTOR,
      Status.HIRED,Person(DateTime.utc(2012, 01, 03),
      197,'01AAC3'),'user10'
  ),
];