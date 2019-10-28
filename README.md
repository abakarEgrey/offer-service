For run the test : mvn test.

For launch the application run : 

The application will be availble at http://localhost:8080/.

For getting available offers send a request like this one hust after lauching the application and before 30 secondes:
	HTTP GET : http://localhost:8080/offers
You get 3 offers. Send this request after 30 secondes, you will get 2 offers

For creating a new offer send a request like this one :
	HTTP POST : http://localhost:8080/offers 
	(Request body)
	{
	  "timeout": 30,
	  "price": {
	  "value": 500,
	  "currency": "EUR"
	  }
	}

For updating an existing offer send a request like this one :
	HTTP PUT : http://localhost:8080/offers/3 
	(Request body)
	{
	  "timeout": 30,
	  "price": {
	  "value": 100,
	  "currency": "USD"
	  }
	}	

For deleting an offer send a request like this one :
	HTTP DELETE : http://localhost:8080/offers/2

For cancelling an offer timeout send a request like this one :
	Create an affer with 60 seconds timout
	HTTP POST : http://localhost:8080/offers 
	(Request body)
	{
	  "timeout": 60,
	  "price": {
	  "value": 500,
	  "currency": "EUR"
	  }
	}
	
	Get all offers and search the id [newOfferId] of new created offer
	HTTP GET : http://localhost:8080/offers (get all )
	
	Cancel the timeout by sending the following request
	HTTP GET : http://localhost:8080/offers/cancel/newOfferId
