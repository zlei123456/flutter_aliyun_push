import 'package:json_annotation/json_annotation.dart'; 
  
part 'example.g.dart';


@JsonSerializable(explicitToJson: true)
  class example extends Object {

  @JsonKey(name: 'pageData')
  PageData pageData;

  @JsonKey(name: 'hosList')
  List<HosList> hosList;

  example(this.pageData,this.hosList,);

  factory example.fromJson(Map<String, dynamic> srcJson) => _$exampleFromJson(srcJson);

  Map<String, dynamic> toJson() => _$exampleToJson(this);

}

  
@JsonSerializable(explicitToJson: true)
  class PageData extends Object {

  @JsonKey(name: 'pageNum')
  int pageNum;

  @JsonKey(name: 'curPage')
  int curPage;

  @JsonKey(name: 'perPage')
  int perPage;

  PageData(this.pageNum,this.curPage,this.perPage,);

  factory PageData.fromJson(Map<String, dynamic> srcJson) => _$PageDataFromJson(srcJson);

  Map<String, dynamic> toJson() => _$PageDataToJson(this);

}

  
@JsonSerializable(explicitToJson: true)
  class HosList extends Object {

  @JsonKey(name: 'id')
  int id;

  @JsonKey(name: 'name')
  String name;

  @JsonKey(name: 'illId')
  int illId;

  @JsonKey(name: 'introduction')
  String introduction;

  @JsonKey(name: 'level')
  String level;

  @JsonKey(name: 'imgurl')
  String imgurl;

  HosList(this.id,this.name,this.illId,this.introduction,this.level,this.imgurl,);

  factory HosList.fromJson(Map<String, dynamic> srcJson) => _$HosListFromJson(srcJson);

  Map<String, dynamic> toJson() => _$HosListToJson(this);

}

  
