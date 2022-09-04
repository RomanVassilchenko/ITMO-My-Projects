import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:lab8/Pages/DataTablePage.dart';
import 'package:lab8/pages/executeCommandPage.dart';
import 'package:lab8/pages/visualizationPage.dart';

import '../utilities/languageDropdownMenu.dart';

class HomePage extends StatefulWidget {

  final String login;

  const HomePage(this.login, {Key? key}) : super(key: key);


  @override
  _HomePageState createState() => _HomePageState(login);
}

class _HomePageState extends State<HomePage> {
  final String login;
  _HomePageState(this.login);
  int _selectedIndex = 0;
  PageController pageController = new PageController();

  void onTapped(int index){
    setState(() {
      _selectedIndex = index;
    });
    pageController.jumpToPage(index);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Lab8 | User: $login"),
        actions: const <Widget>[
          LanguageDropdownMenu()
        ],
      ),
      body: PageView(
        physics: const NeverScrollableScrollPhysics(),
        controller: pageController,
        children: [
          DataTablePage(login),
          ExecuteCommandPage(),
          VisualizationPage()
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(icon: const Icon(Icons.view_column),
              label: 'tableButton'.tr),
          BottomNavigationBarItem(icon: const Icon(Icons.code),
              label: 'commandButton'.tr),
          BottomNavigationBarItem(icon: const Icon(Icons.design_services),
              label: 'visualButton'.tr),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.grey,
        onTap: onTapped,
      ),

    );
  }
}