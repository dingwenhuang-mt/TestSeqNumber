CREATE TABLE test_doc_delivery(
	id SERIAL PRIMARY KEY,
	company_key INTEGER,
	doc_number CHAR(64),
	UNIQUE (company_key, doc_number)
);

CREATE TABLE test_issue_number_rule(
	company_key INTEGER PRIMARY KEY,
	next_serial_number INTEGER
);

INSERT INTO test_issue_number_rule (company_key, next_serial_number) VALUES (100, 101);

