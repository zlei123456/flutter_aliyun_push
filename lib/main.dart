import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'example.dart';


void main() => runApp(ExampleApp());

class ExampleApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) => MaterialApp(
        title: 'Demo',
        theme: ThemeData(accentColor: Colors.pinkAccent),
        home: MyHomePage(),
      );
}

class MyHomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => MyHomePageState();
}

class MyHomePageState extends State<MyHomePage> {
  Map <String, dynamic>resDic = {};


  @override
  void initState() {
    super.initState();

    _initResDic();
    _jsonTest();
  }

  @override
  void dispose() {
    super.dispose();
  }

  /*初始化数据源*/
  _initResDic() {
    resDic = {
        "pageData": {
          "pageNum": 1,
          "curPage": 1,
          "perPage": 10
        },
        "hosList": [
          {
            "id": 190,
            "name": "阜外医院",
            "illId": 1,
            "introduction": "北京阜外医院（全名：中国医学科学院阜外心血管病医院）的前身是解放军胸科医院，始建于1956年，心血管病研究所始建于1962年，由我国胸心外科的奠基人之一吴英恺院士一手创办。北京阜外医院、心血管病研究所是隶属于卫生部、中国医学科学院、中国协和医科大学的三级甲等心血管病专科医院。",
            "level": "三甲",
            "imgurl": "https://t.duodian.api.cheng1hu.com/attachment/hospital/2019/07/01/15/0c365acdfeb18bfc0806a7a922bee800.jpg"
          }
        ]
    };
  }

  _jsonTest() {
    example model = example.fromJson(resDic);
    List <HosList> hosList = model.hosList;
    HosList hosListModel = hosList[0];
    print(model.toJson());
  }
  @override
  Widget build(BuildContext context) => Scaffold(
        body:Text('ttt'),
      );

}
