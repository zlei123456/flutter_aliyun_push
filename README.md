# flutter_simple_tabbar
simple tabbar without TabController, just one widget
<br/>
json转对象文档：https://www.jianshu.com/p/da99262b17df
支持监听页面切换，支持切换tab位置
使用方法：<br/>

        ValueNotifier changeIndex = new ValueNotifier(2);
        this.changeIndex.value = 0; //切换tab，直接设置ValueNotifier的值
        
        SimpleTabBar(
         tabs: tabs,
         changeIndex:changeIndex, //如果需要代码切换tab，可以使用这个属性
         onTabChange:(index) {
           print('onTabChange');
           print(index);
         },
         tabContents: tabs
              .map((Tab tab) =>
              Container(child: Center(child: Text(tab.text),),))
              .toList(),
        ),
      );
        
效果图如下：<br/>
![image](https://img-blog.csdnimg.cn/20200702175908177.jpg)
![image](https://img-blog.csdnimg.cn/20200702175908218.jpg)
## Getting Started
