-- 1. t_user 테이블 생성
CREATE TABLE t_user (
                        seq SERIAL PRIMARY KEY,
                        username VARCHAR(255) UNIQUE NOT NULL,
                        balance INTEGER DEFAULT 0 NOT NULL,
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. t_lottery 테이블 생성 (회차 및 당첨 정보)
CREATE TABLE t_lottery (
                           seq SERIAL PRIMARY KEY,
                           times INTEGER UNIQUE NOT NULL, -- 회차
                           winning_numbers INTEGER[] NOT NULL, -- 당첨 번호 배열
                           bonus_number INTEGER NOT NULL,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. t_lottery_history 테이블 생성 (사용자 응모 내역)
CREATE TABLE t_lottery_history (
                                   seq SERIAL PRIMARY KEY,
                                   lottery_seq INTEGER REFERENCES t_lottery(seq),
                                   user_seq INTEGER REFERENCES t_user(seq),
                                   selected_numbers INTEGER[] NOT NULL, -- 선택 번호 배열
                                   selected_bonus_number INTEGER,
                                   status VARCHAR(50) DEFAULT 'READY', -- READY, WIN, LOSE, PAID 등
                                   winning_rank INTEGER, -- 당첨 등수
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. t_user_money_history 테이블 생성 (자산 변동 이력)
CREATE TABLE t_user_money_history (
                                      seq SERIAL PRIMARY KEY,
                                      user_seq INTEGER REFERENCES t_user(seq),
                                      lottery_history_seq INTEGER REFERENCES t_lottery_history(seq),
                                      type VARCHAR(50) NOT NULL, -- CHARGE, BUY, WIN, WITHDRAW
                                      amount INTEGER NOT NULL,
                                      balance_after INTEGER NOT NULL,
                                      created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 5. 인덱스 설정 (성능 최적화)
CREATE INDEX idx_user_money_history_user_seq ON t_user_money_history(user_seq);
CREATE INDEX idx_lottery_history_user_seq ON t_lottery_history(user_seq);
CREATE INDEX idx_lottery_history_lottery_seq ON t_lottery_history(lottery_seq);

-- 6. updated_at 자동 갱신 트리거 함수 (선택 사항)
-- PostgreSQL은 updated_at을 자동으로 갱신해주지 않으므로 트리거를 사용하면 편리합니다.
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_user_modtime BEFORE UPDATE ON t_user FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_lottery_modtime BEFORE UPDATE ON t_lottery FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_lottery_history_modtime BEFORE UPDATE ON t_lottery_history FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_user_money_history_modtime BEFORE UPDATE ON t_user_money_history FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
