import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:lab8/utilities/languageDropdownMenu.dart';

import 'homePage.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  TextEditingController loginController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text("${"auth".tr} | ${"reg".tr}"),
        actions: const <Widget>[
          LanguageDropdownMenu()
        ],
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Padding(
            //padding: const EdgeInsets.only(left:15.0,right: 15.0,top:0,bottom: 0),
            padding: const EdgeInsets.only(left: 15.0, right: 15.0, top: 15, bottom: 0),
            child: TextField(
              controller: loginController,
              decoration: InputDecoration(
                  border: const OutlineInputBorder(),
                  labelText: ('login'.tr)
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(
                left: 15.0, right: 15.0, top: 15, bottom: 0),
            //padding: EdgeInsets.symmetric(horizontal: 15),
            child: TextField(
              controller: passwordController,
              obscureText: true,
              decoration: InputDecoration(
                  border: const OutlineInputBorder(),
                  labelText: 'password'.tr
              ),
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          Container(
            height: 50,
            decoration: BoxDecoration(
                color: Colors.blue, borderRadius: BorderRadius.circular(20)),
            child: FlatButton(
              onPressed: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (_) => HomePage(loginController.text)));
              },
              child: Text(
                'auth'.tr,
                style: const TextStyle(color: Colors.white, fontSize: 25),
              ),
            ),
          ),
          const SizedBox(
            height: 15,
          ),
          Container(
            height: 50,
            decoration: BoxDecoration(
                color: Colors.blue, borderRadius: BorderRadius.circular(20)),
            child: FlatButton(
              onPressed: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (_) => HomePage(loginController.text)));
              },
              child:  Text(
                'reg'.tr,
                style: const TextStyle(color: Colors.white, fontSize: 25),
              ),
            ),
          ),
        ],
      ),
    );
  }
}