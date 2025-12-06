-- Create indexes for optimizing queries

-- Equipment Model Index
CREATE INDEX IF NOT EXISTS equipment_model_index ON EQUIPMENT(Model);

-- Drone Model Index
CREATE INDEX IF NOT EXISTS drone_model_index ON Drones(Model);

-- Customer Last Name Index
CREATE INDEX IF NOT EXISTS customer_name_index ON CUSTOMERS(L_name);
