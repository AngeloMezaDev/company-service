USE master;
GO

DECLARE @dbname nvarchar(128) = N'CompanyManagement';
DECLARE @sql nvarchar(max);

-- Verificar si la base de datos existe
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = @dbname)
BEGIN
    SET @sql = N'CREATE DATABASE ' + QUOTENAME(@dbname);
    EXEC (@sql);
    PRINT 'Base de datos CompanyManagement creada.';
END
ELSE
BEGIN
    PRINT 'La base de datos CompanyManagement ya existe.';
END
GO

-- Verificar que la base de datos se creó correctamente
IF DB_ID('CompanyManagement') IS NOT NULL
BEGIN
    PRINT 'Verificación: La base de datos CompanyManagement existe.';
END
ELSE
BEGIN
    PRINT 'Error: La base de datos CompanyManagement no se creó correctamente.';
    RETURN 1;
END
GO