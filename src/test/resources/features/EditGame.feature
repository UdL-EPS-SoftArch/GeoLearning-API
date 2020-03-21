Feature: EditGame
  In order to use the app
  As a content creator
  I want to edit a game
  
  
Scenario: Edit instructions of a ImageName
  Given I login as "demo" with password "password"
  And I create a new ImageName with instructions "Relacionar bandera con paises"
	When I edit the ImageName with id "1" and set the instruccions at "El juego consiste en relacionar las banderas con sus paises"
  Then The response code is 200
	And The ImageName with id "1" has the instructions at "El juego consiste en relacionar las banderas con sus paises"
	
	
	