// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'example.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

example _$exampleFromJson(Map<String, dynamic> json) {
  return example(
    json['pageData'] == null
        ? null
        : PageData.fromJson(json['pageData'] as Map<String, dynamic>),
    (json['hosList'] as List)
        ?.map((e) =>
            e == null ? null : HosList.fromJson(e as Map<String, dynamic>))
        ?.toList(),
  );
}

Map<String, dynamic> _$exampleToJson(example instance) => <String, dynamic>{
      'pageData': instance.pageData?.toJson(),
      'hosList': instance.hosList?.map((e) => e?.toJson())?.toList(),
    };

PageData _$PageDataFromJson(Map<String, dynamic> json) {
  return PageData(
    json['pageNum'] as int,
    json['curPage'] as int,
    json['perPage'] as int,
  );
}

Map<String, dynamic> _$PageDataToJson(PageData instance) => <String, dynamic>{
      'pageNum': instance.pageNum,
      'curPage': instance.curPage,
      'perPage': instance.perPage,
    };

HosList _$HosListFromJson(Map<String, dynamic> json) {
  return HosList(
    json['id'] as int,
    json['name'] as String,
    json['illId'] as int,
    json['introduction'] as String,
    json['level'] as String,
    json['imgurl'] as String,
  );
}

Map<String, dynamic> _$HosListToJson(HosList instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'illId': instance.illId,
      'introduction': instance.introduction,
      'level': instance.level,
      'imgurl': instance.imgurl,
    };
