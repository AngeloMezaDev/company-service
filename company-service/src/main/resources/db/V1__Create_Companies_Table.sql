CREATE TABLE companies (
    company_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    company_name NVARCHAR(100) NOT NULL,
    tax_id NVARCHAR(20) NOT NULL,
    address NVARCHAR(200),
    phone NVARCHAR(20),
    email NVARCHAR(100),
    created_date DATETIME2 DEFAULT GETDATE() NOT NULL,
    updated_date DATETIME2,
    is_active BIT DEFAULT 1 NOT NULL,
    CONSTRAINT UK_companies_tax_id UNIQUE (tax_id)
);

CREATE INDEX idx_companies_tax_id ON companies(tax_id);
CREATE INDEX idx_companies_company_name ON companies(company_name);