-- 0. ENUM 타입 정의
CREATE TYPE lottery_purchase_type AS ENUM ('MANUAL', 'AUTO', 'SEMI_AUTO');
CREATE TYPE money_history_type AS ENUM ('CHARGE', 'BUY', 'WIN');
CREATE TYPE lottery_status_type AS ENUM ('READY', 'WIN', 'LOSE');

-- 1. t_user 테이블
CREATE TABLE t_user (
                        seq SERIAL PRIMARY KEY,
                        username VARCHAR(255) UNIQUE NOT NULL,
                        balance INTEGER DEFAULT 0 NOT NULL,
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. t_lottery 테이블
CREATE TABLE t_lottery (
                           seq SERIAL PRIMARY KEY,
                           times INTEGER UNIQUE NOT NULL,
                           winning_numbers INTEGER[] NOT NULL,
                           bonus_number INTEGER NOT NULL,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. t_lottery_history 테이블
CREATE TABLE t_lottery_history (
                                   seq SERIAL PRIMARY KEY,
                                   lottery_seq INTEGER REFERENCES t_lottery(seq),
                                   user_seq INTEGER REFERENCES t_user(seq),
                                   selected_numbers INTEGER[] NOT NULL,
                                   selected_bonus_number INTEGER,
                                   type lottery_purchase_type NOT NULL,
                                   status lottery_status_type DEFAULT 'READY' NOT NULL,
                                   winning_rank INTEGER,
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. t_user_money_history 테이블
CREATE TABLE t_user_money_history (
                                      seq SERIAL PRIMARY KEY,
                                      user_seq INTEGER REFERENCES t_user(seq),
                                      lottery_history_seq INTEGER REFERENCES t_lottery_history(seq),
                                      type money_history_type NOT NULL,
                                      amount INTEGER NOT NULL,
                                      balance_after INTEGER NOT NULL,
                                      created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
