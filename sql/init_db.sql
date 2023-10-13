DROP TABLE IF EXISTS worker CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS project_worker;

CREATE TABLE worker (
    id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(1000) NOT NULL CHECK (LENGTH(name) >= 2 AND LENGTH(name) <= 1000),
    birthday DATE CHECK (birthday > '1900-01-01'),
    level VARCHAR(10) NOT NULL CHECK (level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
    salary INT CHECK (salary >= 100 AND salary <= 100000),
    PRIMARY KEY (id)
);

CREATE TABLE client (
    id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(1000) NOT NULL CHECK (LENGTH(name) >= 2 AND LENGTH(name) <= 1000),
    PRIMARY KEY (id)
);

CREATE TABLE project (
    id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(1000) NOT NULL CHECK (LENGTH(name) >= 2 AND LENGTH(name) <= 1000),
    client_id INT,
    start_date DATE,
    finish_date DATE,
    PRIMARY KEY (id),
    CONSTRAINT project_fk FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE project_worker (
    project_id INT,
    worker_id INT,
    CONSTRAINT project_worker__project_fk FOREIGN KEY (project_id) REFERENCES project(id),
    CONSTRAINT project_worker__worker_fk FOREIGN KEY (worker_id) REFERENCES worker(id)
);