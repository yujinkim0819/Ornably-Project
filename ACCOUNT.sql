CREATE TABLE ACCOUNT(
	ACCOUNT_PK INT PRIMARY KEY, 
	ACCOUNT_ID VARCHAR(50),	
	ACCOUNT_PASSWORD VARCHAR(100), 
	ACCOUNT_NAME VARCHAR(50),	
	ACCOUNT_EMAIL VARCHAR(100),
	ACCOUNT_PHONE VARCHAR(11),	
	ACCOUNT_DATE DATE DEFAULT SYSDATE,
	ACCOUNT_ROLE VARCHAR(50) DEFAULT 'ACCOUNT'
);
--사용자 pk 아이디,비번,이름,이메일,폰번호,날자,유저롤
--유저롤은 기본적으로 유저로 설정함

select * from ACCOUNT;

DROP TABLE ACCOUNT;
DROP TABLE ACCOUNT CASCADE CONSTRAINTS;
-- 테이블 강제삭제
-- 우리 플젝에 fk너무 많음 테이블 삭제 편리성 위해 만듬


--필요한 쿼리문
--회원가입
--회원탈퇴
--비밀번호 확인용
--로그인 확인용
--회원 탈퇴 
--아이디/이메일/폰번호 중복검사
--마이페이지에서 보여줄 개인정보


-- 회원PK 자동증가 시키기 위해서 시퀀스 사용함
-- 시퀀스가 처음 생성될때 시작번호 (처음 번호 1번)
-- 다음번호 생성시 얼마나 증가할지 설정(즉 1씩 증가함)
-- 캐시메모리를 사용하지 않음 번호를 미리 메모리에 저장하지 않는 설정으로 번호가 꼬이거나 건너뛰는 일 방지함
CREATE SEQUENCE ACCOUNT_SEQ
START WITH 8000
INCREMENT BY 1
NOCACHE;

DROP SEQUENCE ACCOUNT_SEQ;
-- 시퀀스 삭제

--로그인
SELECT ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_NAME,ACCOUNT_ROLE
FROM ACCOUNT
WHERE ACCOUNT_ID = 'user01'
AND ACCOUNT_PASSWORD = '1234'
AND ACCOUNT_ROLE = 'ACCOUNT';
-- 로그인시
-- 회원 테이블에서
-- USER01의 아이디와 비번이 1234가 맞는지
-- USER01의 회원 등급이 유저인지
-- 조건에 맞는 한명을 선택한다
--어카운트롤 -> 탈퇴회원 로그인 방지함


--회원 탈퇴시 비번확인용
SELECT COUNT(*)
FROM ACCOUNT WHERE ACCOUNT_PK = '2'
AND ACCOUNT_PASSWORD = '5678'


--회원탈퇴
--마스킹 문자 사용 어떤형태로하는게 좋은가....


UPDATE ACCOUNT
SET 
    ACCOUNT_ID = 'deleted_' || ACCOUNT_PK
WHERE ACCOUNT_PK = '2';
-- 현재 테이블의 ACCOUNT_ID가 NOT NULL 제약조건이 있어서ACCOUNT_ID를 NULL로 바꾸는 대신,
--'deleted_회원PK' 형태의 문자열로 바꾼다.
    -- 예) ACCOUNT_PK = 5 → ACCOUNT_ID = 'deleted_5'
    -- 이유:
    -- 1) NOT NULL 제약이 있어도 값이 있으므로 문제 없음
    -- 2) UNIQUE 제약이 있어도 PK를 붙여주기 때문에 절대 중복되지 않음
    -- 3) 로그인 차단 효과 있음 (삭제된 사용자임을 명확하게 표현)
	-- 탈퇴시키려는 정확한 회원을 찾기 위해 PK 기준으로 업데이트한다.


--중복검사 확인
--카운트가 1이상 나오면 중복이라는 뜻이다
-- 아이디
SELECT COUNT(*) FROM ACCOUNT WHERE ACCOUNT_ID = 'user01';

-- 이메일
SELECT COUNT(*) FROM ACCOUNT 
WHERE ACCOUNT_EMAIL = 'user01@TEST.COM';

-- 전화번호 중복 검사
SELECT COUNT(*) FROM ACCOUNT 
WHERE ACCOUNT_PHONE = '01012345678';

-- COUNT(*) 행의 개수를 세서 컬럼 NULL 여부와 상관없이 정확한 행의 수를 제공한다
-- COUNT(컬럼명)은 컬럼이 널일경우 카운트에서 제외되서 중복검사시 문제가 될수 있다

-- 마이페이지에서 보여줄 개인정보
SELECT ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_NAME,ACCOUNT_EMAIL,ACCOUNT_PHONE
FROM ACCOUNT WHERE ACCOUNT_PK = '2';
		
--샘플 01
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user01','aB3!kP9$','홍길동','user01@TEST.COM','01012345678'
);

--샘플 02
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user02','Zm!7Qe@2','이몽룡','user02@test.com','01012345679'
);

-- 샘플 03
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user03','p@C9K!s4', '변희인','heein@test.com','01012364587'
);

-- 샘플 04
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user04','!G2xM7$d','김유진','ujin@test.com','01052136548'
);

-- 샘플 05
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user05','Wanny2005!@','허완','hurwan@naver.com','01056248975'
);

-- 샘플 06
INSERT INTO ACCOUNT (
	ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,
	ACCOUNT_PHONE
)
VALUES(
	ACCOUNT_SEQ.NEXTVAL,'user06','cD$4!PZ7','정송이','songe@naver.com','01098741324'
);

SELECT * FROM ACCOUNT;