DROP PROCEDURE IF EXISTS get_order_details;

CREATE PROCEDURE get_order_details(IN orderId INT)
	SELECT * FROM Orders WHERE Order_ID = orderId; 

