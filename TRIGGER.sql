-- 트리거 만들기
-- ADDRESS 테이블에 추가하거나 업데이트를 실행하기 직전에 작동된다
-- 즉 사용자가 새로운 주소를 추가하거나 기존 주소지를 수정할때 실행함
CREATE OR REPLACE TRIGGER TRG_ADDRESS_DEFAULT
BEFORE INSERT OR UPDATE ON ADDRESS
FOR EACH ROW
BEGIN
    -- 기본배송지로 체크된 경우만 트리거 실행
    IF :NEW.ADDRESS_IS_DEFAULT = 1 THEN

        -- 신규 등록(INSERT)일 경우: 기존 기본배송지를 모두 0으로 초기화
        IF INSERTING THEN
            UPDATE ADDRESS
            SET ADDRESS_IS_DEFAULT = 0
            WHERE ACCOUNT_PK = :NEW.ACCOUNT_PK;

        -- 수정(UPDATE)일 경우: 본인을 제외한 기존 배송지를 0으로 초기화
        ELSIF UPDATING THEN
            UPDATE ADDRESS
            SET ADDRESS_IS_DEFAULT = 0
            WHERE ACCOUNT_PK = :NEW.ACCOUNT_PK
              AND ADDRESS_PK <> :NEW.ADDRESS_PK;
        END IF;

    END IF;
END;
/

-- INSERT일 때: 자기 주소 PK가 아직 없을 수 있으므로 PK 비교 X
-- 같은 회원의 모든 기존 배송지 기본값을 0으로 초기화함.
-- 이후 새로 INSERT된 주소가 기본배송지가 됨.

-- UPDATE일 때: 이미 PK가 존재하므로 자기 자신 제외 필요
-- 같은 회원이 가진 다른 배송지들의 기본값만 해제함.
-- UPDATE되는 해당 주소만 기본배송지로 유지됨.

-- 트리거 삭제하기
DROP TRIGGER TRG_ADDRESS_DEFAULT;

-- 트리거 상태확인
SELECT TRIGGER_NAME, STATUS
FROM USER_TRIGGERS
WHERE TRIGGER_NAME = 'TRG_ADDRESS_DEFAULT';

-- 트리거 에러메세지 확인
SELECT * FROM USER_ERRORS
WHERE NAME = 'TRG_ADDRESS_DEFAULT';

--DB에 저장된 트리거 내용 보기
SELECT TEXT
FROM USER_SOURCE
WHERE NAME = 'TRG_ADDRESS_DEFAULT'
ORDER BY LINE;




