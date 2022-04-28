# Messenger ( Socket Programming )

> 간단한 온라인 메신저 프로그램입니다.  
> Multi-thread를 이용하여 여러명의 사용자가 동시에 메세지를 주고받을 수 있고  
> Client-Server 구조에 기반한 소켓 프로그래밍을 사용했습니다.  
> 회원가입, 로그인, 아이디-비밀번호 찾기, 날씨 확인 등의 기능이 구현되었습니다.

## 전체 구조도

<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger1.png?raw=true">
</p>

- Connection : Socket을 이용한 연결
- Login&Register : 서버DB를 이용해 ID와PW 및 개인정보 기록
- Manage User Data : 로그인 성공시 서버-클라이언트 간 정보 공유
- Chatting : multi-thread를 이용하여 1대1 채팅 & 1대多 채팅 가능
- Open Data API : API를 가져와 당일 날씨 확인가능

## Table of Contents
- **회원가입**
	- ID, PW, Nickname, 이메일, 생년월일, 전화번호, 홈페이지 정보 입력 후 DB에 저장

- **로그인**
	- DB에 저장된 회원정보와 일치 할 시 로그인 가능

- **ID/PW 찾기**
	- DB에 저장된 회원정보를 기반으로 ID/PW 찾기 가능

- **회원정보수정**

- **사용자 검색**
	- 메신저를 이용중인 다른 사용자 검색 가능

- **사용자 친구 추가**

- **일대일 채팅**

- **일대多 채팅**
	- 오픈채팅 형식으로 여러명과 동시에 채팅 가능

- **날씨 확인**
	- 날씨 확인 API를 사용하여 그날의 날씨 확인 가능

## 사용 예시
**[ 서버 연동시 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger3.png?raw=true">
</p>
<br>

**[ 로그인 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger4.png?raw=true">
</p>
<br>

**[ 회원가입 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger2.png?raw=true">
</p>
<br>

**[ 회원가입 조건 미충족시 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger5.png?raw=true">
</p>
<br>


**[ 비밀번호 찾기 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger6.png?raw=true">
</p>
<br>

**[ 채팅창 기본 화면 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger7.png?raw=true">
</p>
<br>

**[ 사용자 검색 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger8.png?raw=true">
</p>
<br>

**[ 정보 변경 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger8.png?raw=true">
</p>
<br>

**[ 채팅 - 1대1 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger9.png?raw=true">
</p>
<br>

**[ 채팅 - 1대1 ] (2)**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger10.png?raw=true">
</p>
<br>

**[ 채팅 - 1대多 ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger11.png?raw=true">
</p>
<br>

**[ 날씨 확인 API ]**
<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger12.png?raw=true">
</p>
<br>


## 설치 방법
**개발환경**

> JAVA 버전 : 17.0.2  
> 서버 : Client-Server, Socket Programming  
> Eclipse IDE for Enterprise and Web Developers - 2021.12  
> DB : MySQL-connector-java-8.0.22  

**개발 환경이 모두 갖춰진 뒤에는 로컬 환경에서 바로 실행 가능합니다!**  
**단, DB연동을 위해 MySQL-connector-java-8.0.22를 프로젝트 외부 라이브러리에 등록해줘야 합니다.**


## 설명
- **메신저 :** 중형 규모의 네트워크 메신저 프로그램입니다. 접속 및 회원가입 /수정, 로그인, 사용자/친구관리, 1대1채팅방, 1대多채팅방, 공공데이터 API 기능이 구현되어 있습니다. 
- **Multi-Thread** :  멀티 쓰레드를 사용하여 한번에 여러명과 채팅을 할 수 있습니다.
- **DBMS** : DBMS(JDBC)를 이용하여 사용자 정보 관리를 할 수 있습니다.
- **API** : 오늘의 날씨를 확인할 수 있는 공공데이터 API를 사용했습니다.

## 보안
**암호화 ( Encrypt ) 복호화 ( Decrypt)**
```java
private final static String HEX = "0123456789ABCDEF";
    private final static String key = "AABBCCDDEERRFF";
    
    public static String encrypt(String cleartext) throws Exception
    {
        String seed = key;
    	byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result); 
    }

    public static String decrypt(String encrypted) throws Exception
    {
        String seed = key;
    	byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
```
문자열로 받은 사용자의 비밀번호를 암호화 하는 코드의 일부이다. 회원가입 시 입력받은 비밀번호를 **AES알고리즘**을 이용하여 암호화 하였다. 

## API

**공공데이터 포털 : 기상청_동네예보 조회서비스**

<p align="left">
<img src="https://github.com/idkim97/idkim97.github.io/blob/master/img/messenger13.png?raw=true">
</p>
