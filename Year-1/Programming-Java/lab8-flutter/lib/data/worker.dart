import 'package:lab8/data/position.dart';
import 'package:lab8/data/status.dart';

class Worker {
  int id; //Поле не может быть null, Значение поля должно быть больше 0,
  // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
  String name; //Поле не может быть null, Строка не может быть пустой
  Coordinates coordinates; //Поле не может быть null
  DateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
  int salary; //Поле может быть null, Значение поля должно быть больше 0
  Position position; //Поле не может быть null
  Status status; //Поле не может быть null
  Person person; //Поле не может быть null
  String author;

  Worker(this.id, this.name, this.coordinates, this.creationDate, this.salary,
      this.position, this.status, this.person, this.author);

}

class Coordinates {
  int x;
  double y;
  Coordinates(this.x, this.y);
}

class Person {
  DateTime birthday;
  double height;
  String passportID;
  Person(this.birthday, this.height, this.passportID);
}