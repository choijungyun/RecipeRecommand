# 만성질환을 고려한 개인 맞춤형 레시피 추천 앱

    주요 내용 : 사용자의 신체정보, 만성 질환, 기피 음식등의 정보를 활용한 레시피 기반 내용 추천 알고리즘 설계   
    기간 : 2018.03 - 2018.11   
    개발 인원 : 4인    
    역할 : SW Developer   
    개발 언어 : JAVA, PHP, MySQL 사용    
    개발 환경 : AndroidStudio    

**1. 개발 동기 및 목표**   
현대인들의 건강에 대한 관심이 높아짐과 동시에 활발한 모바일 어플리케이션 개발이 이루어지면서 식단관리 모바일 어플리케이션에 대한 수요가 늘어나고 있다.    야후 플러리 보고서에 따르면 건강 관리 모바일 어플리케이션은 2014년-2017년 3년동안 3배이상 증가되었다.    하지만 건강 관리 모바일 어플리케이션중 식단기록 앱의 경우 오히려 그 수가 감소되었으며, 2017년도에는 전체 중에 1%만을 차지하게 되었다.   
이는 사람들의 관심이 나날이 증가하고 있음에도 불구하고 대다수의 어플리케이션들은 다이어트에 초점을 맞춰 제한적인 기능만 구현하고 있고, 단순한 식단기록만을 제공할 뿐, 식단기록을 통한 피드백이나 개인 맞춤 추천 기능에 대해서는 제공하지 않기 때문이다.    뿐만 아니라 사용자의 역할은 식단을 입력하는 것에 한정되어 있기 때문에 사용자의 흥미를 유지하기 어렵다는 단점도 가지고 있다.   
따라서 본 시프템에서는 일반 사용자뿐만 아니라 만성질환을 가진 사용자까지 폭 넓게 수용할 수 있도록 설계되었고, 사용자의 지속적인 사용을 위해, 식단 기록의 편의성을 제공하고 있다.    또한 사용자는 식단기록뿐만 아니라 사용자의 데이터를 통한 레시피 추천까지 받아볼 수 있기 때문에 사용자의 적극적인 참여를 유도할 수 있을 거라 기대할 수 있다.   



**2. 개발 언어 및 환경**

OS 운영환경은 Windows10 아래에서 진행되었다.    주요 개발 언어는 Android Studio로, 각종 Google Open API를 활용해 어플의 폭넓은 세부 기능과 깔끔한 UI를 제작하였다.    DataBase는 Oracle을 활용하였고, Android Studio와 DataBase의 데이터 전달을 위해 서버 언어로는 PHP를 사용하여 개발을 진행하였다.    DB는 Cafe24에서 서버 호스팅을 받아 PHPMyAdmin에서 SQL쿼리를 이용하여 데이터를 관리하였다.


**3. 사용기술 및 특징**

Application 설계에 사용되는 이론은  [4]보건복지부 ‘2015 한국인 영양소 섭취’ 를 참고하였다. 
Application 구현시 Google API ( Google zebra crossing API)와 Android Studio API( Android Speech API, Android camera API) 등을 사용하여 필요한 기능을 구현며, Android와 MySQL 데이터를  PHP를 통해 주고 받으면서 Application에 필요한 알고리즘을 구현한다.
레시피 추천시, 아이템 기반 협업 필터링(Item-based Collaborative Filtering)을 가공한 알고리즘을 사용된다. 아이템 기반 협업 필터링의 경우, 데이터가 방대하고 변경이 자주 일어나지 않는 경우 아이템별 유사도를 이용해 가장 유사한 아이템을 추천할 수 있다는 장점이 있다. 본 시스템의 database는 식품안전나라의 레시피 DB에 대해서만 추천이 일어나기때문에 이 기법에 적합하다고 할 수 있다. 
따라서 필자는 사용자의 선호도를 조사하기 위해 레시피 추천 전 요리종류와 가격대 필터를 사용하여 사용자의 선택을 받으면서 추천이 시작되도록 하였고, 선별된 요리법 중에서 냉장고에 있는 아이템과 일치하는 갯수가 많으면서, 사용자의 영양소 균형과 만성질환을 고려한 레시피가 나올 수 있도록 하였다. 또한 식단기록시에는 식단기록 DB에 저장된 음식중 일정 횟수이상 섭취한 음식을 리스트목록으로 화면에 제공하여 키보드 입력없이 클릭만으로도 입력이 되도록 하는 알고리즘도 간단히 구현하였다.

**4. 기능 설명 및 구조도**
![image {: width="50" height="50"}](https://user-images.githubusercontent.com/25261332/89543647-247c8880-d83c-11ea-9bc0-898a1de76ffc.png)

**내용 기반 추천 알고리즘**
![image](https://user-images.githubusercontent.com/25261332/89698039-102ead80-d95a-11ea-9218-f1a51224cf57.png)
![image](https://user-images.githubusercontent.com/25261332/89698070-276d9b00-d95a-11ea-9af9-08267d108d1b.png)



