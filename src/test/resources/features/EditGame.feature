Feature: EditGame
  In order to use the app
  As a content creator
  I want to edit a game
  
  
Scenario: Edit instructions of a ImageName
  Given I login as "creator" with password "password"
  And I create a new ImageName with instructions "Relacionar bandera con paises"
	When I edit the ImageName and set the instruccions at "El juego consiste en relacionar las banderas con sus paises"
  Then The response code is 200
	And The ImageName has the instructions at "El juego consiste en relacionar las banderas con sus paises"
	
Scenario: Edit instructions of a ImageImage
	  Given I login as "creator" with password "password"
    And I create a new ImageOption with instructions "Relacionar bandera con paises"
    When I edit the ImageImage and set the instructions at "El juego consiste en relacionar las banderas con sus paises"
    Then The response code is 200
    And The ImageImage has the instructions at "El juego consiste en relacionar las banderas con sus paises"

Scenario: Edit instructions of a ImageOption	
    Given I login as "creator" with password "password"
    And I create a new ImageOption with instructions "Elige la respuesta correcta"
    When I edit the ImageOption and set the instructions at "El juego consiste en elegir la respuesta correcta"
    Then The response code is 200
    And The ImageOption has the instructions at "El juego consiste en elegir la respuesta correcta"
     
	