import 'package:flutter/material.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/route_manager.dart';

class LanguageDropdownMenu extends StatefulWidget {
  const LanguageDropdownMenu({Key? key}) : super(key: key);

  @override
  State<LanguageDropdownMenu> createState() => _LanguageDropdownMenuState();
}

class _LanguageDropdownMenuState extends State<LanguageDropdownMenu> {
  String dropdownValue = 'ru_RU';

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      value: dropdownValue,
      icon: const Icon(Icons.arrow_downward),
      elevation: 16,
      style: const TextStyle(color: Colors.deepPurple),
      underline: Container(
        height: 2,
        color: Colors.deepPurpleAccent,
      ),
      onChanged: (String? newValue) {
        setState(() {
          dropdownValue = newValue!;
          var locale = Locale(newValue);
          Get.updateLocale(locale);
        });
      },
      items: <String>['ru_RU','de_DE','es_MX','lt_LT']
          .map<DropdownMenuItem<String>>((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
    );
  }
}