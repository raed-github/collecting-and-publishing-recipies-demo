# Rest API for recipies

# Table of contents
- [Introduction](#Introduction)
- [Summary](#Summary)
- [Requirements](#Requirements)
- [Design](#Design)
- [ObjectModel](#ObjectModel)
- [Representation](#Representation)
- [RecipeApiModelUriAndHttpMethods](#RecipeApiModelUriAndHttpMethods)
- [ArchitecturalConstraints](#ArchitecturalConstraints)
- [DatabaseDesignAndRelations](#DatabaseDesignAndRelations)
- [TechStack](#TechStack)
- [Maven](#Maven)
- [Testing](#Testing)
___
### Spring Boot recipe api

---
### Introduction
This Api create, retrives, and filter recipies based on a given url and query string and store it into the H2 in memory database

### Summary
This Api was created to create recipies with their categories and data.

#### Requirements
##UserStory1:As a web designer I would like to retrieve recipes from the back-end system so I can display them in my app

#UserStory1 Requirements:
- Without any additional query parameters, should return all recipes known to the back-end service
- Support filtering based on recipe category
- Support search strings, with the service then trying to match these in relevant fields (for example name and category)

## UserStory2 - As a web designer I would like to retrieve the available recipe categories so I can do more focused requests for specific recipe types

# UserStory2 Requirements: 
- Operation returns all recipe categories

## UserStory3 - As a web designer I want to be able to add new recipes, so I can expand the recipe database with new, tasty and inspiring recipes

# UserStory Requirements:
- When given valid input, creates a new recipe in the backend which can then be retrieved by the service's clients
- Make sure the provided input is valid
- Do not allow multiple recipes with the same name (so people don't get confused)

### Design
The application has 1 api inside a maven parent project
* recipe-api

```html
POST /api/v1/recipies
```
#### ObjectModel
- Recipe
- Category
- Direction
- Ingredient


#### Representation
category:
```html
    {
        "id":1,
        "category":"vegitarian"
    }
```
Ingredient:
```html
    {
        "ingredient":"heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
        "quantity":650,
        "unit":"gram"
    }
```
Direction:
```html
    [{
        "step":"Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
    }]

```
Recipe:

```html
{
    "name":"recipe2",
    "yield":1,
    "difficultyLevel":"easy",
    "preparationTime":"25 mins",
    "serves":"4",
    "cookingTime":"lunch",
    "directions":[{
            "step":"Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
        },
        {
            "step":"Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve"
        }
    ],
    "categories":[
        {
            "id":1,
            "category":"vegitarian"
        }
    ],
    "ingredients":[
        {
            "ingredient":"heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
            "quantity":650,
            "unit":"gram"
        },
        {
            "ingredient":"red onion, finely sliced",
            "quantity":0.5
        }
    ]
}
```

#### RecipeApiModelUriAndHttpMethods

| API Name | HTTP Method | Model URIs | Status code | Description | 
| --------------- | --------------- | --------------- | --------------- | --------------- |
| recipe-api | POST| /api/v1/recipies | 201 (CREATED)| Creating a recipe |
| recipe-api | GET| /api/v1/recipies |  200 (OK)| Get all recipies |
| recipe-api | GET| /api/v1/recipies/{id} |  200 (OK)| Get recipe by id |
| recipe-api | POST| /api/v1/categories | 201 (CREATED)| Creating a category |
| recipe-api | POST| /api/v1/categories | 400 (Bad Request)| field validation error |
| recipe-api | GET| /api/v1/categories | 200 (OK)| Retrieve all categories |
| recipe-api | GET| /api/v1/categories/{id} | 201 (OK)| Retrieve category by id |
| recipe-api | GET| /api/v1/categories/{id} | 201 (OK)| Retrieve category by id |
| recipe-api | GET| /api/v1/categories/{id}/recipies | 201 (OK)| Retrieve category recipies |
| recipe-api | GET| /api/v1/recipies?name=recipe1&category=category1&sort=name&direction=desc |  200 (OK)| Get recipe by id |

### ArchitecturalConstraints

### Uniform interface

1- All resources in the api can be fetched using a logical URI
2- HATEOAS are added inorder to avoid large resources
3- All resource representations accross the system follow naming conventions, and links best pratices
4- All resources are accessible through a common approach such as HTTP GET and similarly modified using a consistent approach.


### Client–server

1-client application (MX) and server application (recipe-api) are able to evolve separately without any dependency on each other

### Stateless

1-The server will not store anything about the latest HTTP request the client made. It will treat every request as new. No session, no history

### Layered system

1-recipe-api follows layered system architecture where you deploy the APIs on server A, and store data on server B and authenticate requests in Server C.

## DatabaseDesignAndRelations

| TableName | Reference Object | Association | ReferenceTable | Description | 
| --------------- | --------------- | --------------- | --------------- | --------------- |
| Recipe | Recipe| ManyToMany |Categories | A recipe can be linked to many categories and a one categories can be used by many recipies |
| Recipe | Recipe| ManyToMany |  Ingredients | A recipe can have multiple ingredients and same ingredient can be used by multiple other recipies |
| Recipe | Recipe| OneToMany |  Direction | A recipe can have one or more directions to accomplish |
| Category | Category| ManyToMany |  Recipe | A Category can be linked to many recipies |
| Direction | Direction| ManyToOne |  Recipe | Many directions can be linked to one recipe |
| Ingredient | Ingredient| ManyToMany |  Recipe | Same ingredient can be used by multiple categories |

# JUnit & integration, Acceptance tests coverage.

### TechStack

---
- Java 17
- Spring Boot
- Spring Data JPA
- Spring AOP
- Spring Validation
- Hateoas
- Spring docs openapi-ui
- Restful API
- Lombok
- H2 Database  
- JUnit 5

### Prerequisites

---
- Maven
- Java 17
- Lombok
- H2
- AOP
- Spring Validation
- Hateoas
- Spring docs openapi-ui

### Run & Build

---
There are 2 ways of run & build the application.

#### Docker Compose

For docker compose usage, docker images already push to docker.io

You just need to run `docker-compose up` command
___
*$PORT: *
```ssh
$ cd  recipe-api
$ docker-compose up
```

#### Maven

___
*$PORT: *
```ssh
$ cd  c:/technical-assignment/recipe-api
$ mvn clean install
$ mvn spring-boot:run

```

### Testing
- Integration and Acceptance testing are coverd for this api and can be executed successfully

- Postman Testing

test case 1: Create a correct Category Test Case
```ssh
    {
        "category":"vegitarian"
    }

{
    "id": 1,
    "category": "vegitarian",
    "recipiesLink": "http://localhost:8081/api/v1/categories/1/recipies",
    "_links": {
        "self": {
            "href": "http://localhost:8081/api/v1/categories/1"
        },
        "recipies": {
            "href": "http://localhost:8081/api/v1/categories/1/recipies"
        }
    }
}
```

test case 2: Create a category with existing name
```ssh
    {
        "category":"vegitarian"
    }

{
    "message": "Category name already exist",
    "date": "2023-05-31"
}
```

testcase 3:Create a category with no name
```ssh
    {
    
    }

{
    "fieldErrors": [
        {
            "message": "Category category is a mandatory input",
            "date": "2023-05-31"
        }
    ]
}
```

testcase 4: Retrieve all categories
```ssh
http://localhost:8081/api/v1/categories

[
    {
        "id": 1,
        "category": "vegitarian",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/api/v1/categories/1"
            },
            {
                "rel": "recipies",
                "href": "http://localhost:8081/api/v1/categories/1/recipies"
            }
        ]
    }
]
```

testcase 5: Retrieve category by id
```ssh
http://localhost:8080/api/v1/categories/1

{
    "id": 1,
    "category": "vegitarian"
}
```

test case 6: retrieve category recipies
```ssh
localhost:8081/api/v1/categories/1/recipies

[
    {
        "recipeId": 1,
        "name": "recipe2",
        "yield": 1,
        "preparationTime": "25 mins",
        "difficultyLevel": "easy",
        "serves": "4",
        "cookingTime": "lunch",
        "directions": [
            {
                "step": "Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
            },
            {
                "step": "Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
            },
            {
                "step": "Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
            },
            {
                "step": "Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
            }
        ],
        "categories": [
            {
                "id": 1,
                "category": "vegitarian",
                "links": [
                    {
                        "rel": "self",
                        "href": "http://localhost:8081/api/v1/categories/1"
                    },
                    {
                        "rel": "recipies",
                        "href": "http://localhost:8081/api/v1/categories/1/recipies"
                    }
                ]
            }
        ],
        "ingredients": [
            {
                "ingredient": "flat-leaf parsley, leaves picked",
                "quantity": 1.0,
                "unit": "pack"
            },
            {
                "ingredient": "small capers, drained",
                "quantity": 2.0,
                "unit": "tsp"
            },
            {
                "ingredient": "good quality sherry vinegar",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "extra virgin olive oil",
                "quantity": 150.0,
                "unit": "ml"
            },
            {
                "ingredient": "ricotta",
                "quantity": 50.0,
                "unit": "gram"
            },
            {
                "ingredient": "Dijon mustard",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "garlic, finely chopped",
                "quantity": 1.0,
                "unit": "clove"
            },
            {
                "ingredient": "heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
                "quantity": 650.0,
                "unit": "gram"
            },
            {
                "ingredient": "red onion, finely sliced",
                "quantity": 0.5,
                "unit": null
            },
            {
                "ingredient": "podded broad beans",
                "quantity": 100.0,
                "unit": "gram"
            },
            {
                "ingredient": "chervil, leaves only",
                "quantity": 1.0,
                "unit": "pack"
            }
        ],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/api/v1/recipies/1"
            }
        ]
    }
]
```
testcase 7: Create recipe
```ssh
{
	"name":"recipe3",
	"yield":1,
	"difficultyLevel":"easy",
	"preparationTime":"25 mins",
	"serves":"4",
	"cookingTime":"lunch",
    "directions":[{
		"step":"Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
	},
	{
		"step":"Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
	},
	{
		"step":"Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
	},
	{
		"step":"Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
	}
	],
    "categories":[
		{
            "id":1,
			"category":"vegitarian"
		}
	],
    "ingredients":[
    	{
			"ingredient":"heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
	        "quantity":650,
	        "unit":"gram"
        },
        {
			"ingredient":"red onion, finely sliced",
	        "quantity":0.5
        },
        {
			"ingredient":"podded broad beans",
	        "quantity":100,
	        "unit":"gram"   
        },
        {
			 "ingredient":"ricotta",
	         "quantity":50,
	         "unit":"gram"
	    },
        {
			"ingredient":"flat-leaf parsley, leaves picked",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"chervil, leaves only",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"garlic, finely chopped",
	        "quantity":1,
	        "unit":"clove"            
        },
        {
			"ingredient":"Dijon mustard",
	        "quantity":1,
	        "unit":"tsp"
        },
        {
			"ingredient":"small capers, drained",
	        "quantity":2,
	        "unit":"tsp"            
        },
        {
			"ingredient":"extra virgin olive oil",
	        "quantity":150,
	        "unit":"ml"            
        },
        {
			"ingredient":"good quality sherry vinegar",
	        "quantity":1,
	        "unit":"tsp"
	}
    ]
}
{
    "recipeId": 2,
    "name": "recipe3",
    "yield": 1,
    "preparationTime": "25 mins",
    "difficultyLevel": "easy",
    "serves": "4",
    "cookingTime": "lunch",
    "directions": [
        {
            "step": "Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
        },
        {
            "step": "Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
        },
        {
            "step": "Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
        },
        {
            "step": "Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
        }
    ],
    "categories": [
        {
            "id": 1,
            "category": "vegitarian",
            "_links": {
                "self": {
                    "href": "http://localhost:8081/api/v1/categories/1"
                },
                "recipies": {
                    "href": "http://localhost:8081/api/v1/categories/1/recipies"
                }
            }
        }
    ],
    "ingredients": [
        {
            "ingredient": "flat-leaf parsley, leaves picked",
            "quantity": 1.0,
            "unit": "pack"
        },
        {
            "ingredient": "small capers, drained",
            "quantity": 2.0,
            "unit": "tsp"
        },
        {
            "ingredient": "good quality sherry vinegar",
            "quantity": 1.0,
            "unit": "tsp"
        },
        {
            "ingredient": "extra virgin olive oil",
            "quantity": 150.0,
            "unit": "ml"
        },
        {
            "ingredient": "ricotta",
            "quantity": 50.0,
            "unit": "gram"
        },
        {
            "ingredient": "Dijon mustard",
            "quantity": 1.0,
            "unit": "tsp"
        },
        {
            "ingredient": "heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
            "quantity": 650.0,
            "unit": "gram"
        },
        {
            "ingredient": "garlic, finely chopped",
            "quantity": 1.0,
            "unit": "clove"
        },
        {
            "ingredient": "red onion, finely sliced",
            "quantity": 0.5,
            "unit": null
        },
        {
            "ingredient": "chervil, leaves only",
            "quantity": 1.0,
            "unit": "pack"
        },
        {
            "ingredient": "podded broad beans",
            "quantity": 100.0,
            "unit": "gram"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8081/api/v1/recipies/2"
        }
    }
}
```

testcase 8: Create recipe with existing name
```ssh
{
	"name":"recipe3",
	"yield":1,
	"difficultyLevel":"easy",
	"preparationTime":"25 mins",
	"serves":"4",
	"cookingTime":"lunch",
    "directions":[{
		"step":"Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
	},
	{
		"step":"Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
	},
	{
		"step":"Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
	},
	{
		"step":"Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
	}
	],
    "categories":[
		{
            "id":1,
			"category":"vegitarian"
		}
	],
    "ingredients":[
    	{
			"ingredient":"heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
	        "quantity":650,
	        "unit":"gram"
        },
        {
			"ingredient":"red onion, finely sliced",
	        "quantity":0.5
        },
        {
			"ingredient":"podded broad beans",
	        "quantity":100,
	        "unit":"gram"   
        },
        {
			 "ingredient":"ricotta",
	         "quantity":50,
	         "unit":"gram"
	    },
        {
			"ingredient":"flat-leaf parsley, leaves picked",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"chervil, leaves only",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"garlic, finely chopped",
	        "quantity":1,
	        "unit":"clove"            
        },
        {
			"ingredient":"Dijon mustard",
	        "quantity":1,
	        "unit":"tsp"
        },
        {
			"ingredient":"small capers, drained",
	        "quantity":2,
	        "unit":"tsp"            
        },
        {
			"ingredient":"extra virgin olive oil",
	        "quantity":150,
	        "unit":"ml"            
        },
        {
			"ingredient":"good quality sherry vinegar",
	        "quantity":1,
	        "unit":"tsp"
	}
    ]
}

{
    "message": "Recipe category already used",
    "date": "2023-05-31"
}
```
testcase 9: Retrieve all recipies
```ssh
localhost:8081/api/v1/recipies1

[
    {
        "recipeId": 1,
        "name": "recipe2",
        "yield": 1,
        "preparationTime": "25 mins",
        "difficultyLevel": "easy",
        "serves": "4",
        "cookingTime": "lunch",
        "directions": [
            {
                "step": "Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
            },
            {
                "step": "Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
            },
            {
                "step": "Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
            },
            {
                "step": "Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
            }
        ],
        "categories": [
            {
                "id": 1,
                "category": "vegitarian",
                "links": [
                    {
                        "rel": "self",
                        "href": "http://localhost:8081/api/v1/categories/1"
                    },
                    {
                        "rel": "recipies",
                        "href": "http://localhost:8081/api/v1/categories/1/recipies"
                    }
                ]
            }
        ],
        "ingredients": [
            {
                "ingredient": "small capers, drained",
                "quantity": 2.0,
                "unit": "tsp"
            },
            {
                "ingredient": "flat-leaf parsley, leaves picked",
                "quantity": 1.0,
                "unit": "pack"
            },
            {
                "ingredient": "good quality sherry vinegar",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "extra virgin olive oil",
                "quantity": 150.0,
                "unit": "ml"
            },
            {
                "ingredient": "ricotta",
                "quantity": 50.0,
                "unit": "gram"
            },
            {
                "ingredient": "Dijon mustard",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
                "quantity": 650.0,
                "unit": "gram"
            },
            {
                "ingredient": "garlic, finely chopped",
                "quantity": 1.0,
                "unit": "clove"
            },
            {
                "ingredient": "red onion, finely sliced",
                "quantity": 0.5,
                "unit": null
            },
            {
                "ingredient": "podded broad beans",
                "quantity": 100.0,
                "unit": "gram"
            },
            {
                "ingredient": "chervil, leaves only",
                "quantity": 1.0,
                "unit": "pack"
            }
        ],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/api/v1/recipies/1"
            }
        ]
    },
    {
        "recipeId": 2,
        "name": "recipe3",
        "yield": 1,
        "preparationTime": "25 mins",
        "difficultyLevel": "easy",
        "serves": "4",
        "cookingTime": "lunch",
        "directions": [
            {
                "step": "Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
            },
            {
                "step": "Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
            },
            {
                "step": "Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
            },
            {
                "step": "Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
            }
        ],
        "categories": [
            {
                "id": 1,
                "category": "vegitarian",
                "links": [
                    {
                        "rel": "self",
                        "href": "http://localhost:8081/api/v1/categories/1"
                    },
                    {
                        "rel": "recipies",
                        "href": "http://localhost:8081/api/v1/categories/1/recipies"
                    }
                ]
            }
        ],
        "ingredients": [
            {
                "ingredient": "small capers, drained",
                "quantity": 2.0,
                "unit": "tsp"
            },
            {
                "ingredient": "flat-leaf parsley, leaves picked",
                "quantity": 1.0,
                "unit": "pack"
            },
            {
                "ingredient": "good quality sherry vinegar",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "extra virgin olive oil",
                "quantity": 150.0,
                "unit": "ml"
            },
            {
                "ingredient": "ricotta",
                "quantity": 50.0,
                "unit": "gram"
            },
            {
                "ingredient": "Dijon mustard",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "garlic, finely chopped",
                "quantity": 1.0,
                "unit": "clove"
            },
            {
                "ingredient": "heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
                "quantity": 650.0,
                "unit": "gram"
            },
            {
                "ingredient": "red onion, finely sliced",
                "quantity": 0.5,
                "unit": null
            },
            {
                "ingredient": "podded broad beans",
                "quantity": 100.0,
                "unit": "gram"
            },
            {
                "ingredient": "chervil, leaves only",
                "quantity": 1.0,
                "unit": "pack"
            }
        ],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/api/v1/recipies/2"
            }
        ]
    }
]
```
testcase 10: Retrieve all recipies
```ssh
localhost:8081/api/v1/recipies?name=recipe2&category=vegitarian&sort=name&direction=desc
[
    {
        "recipeId": 1,
        "name": "recipe2",
        "yield": 1,
        "preparationTime": "25 mins",
        "difficultyLevel": "easy",
        "serves": "4",
        "cookingTime": "lunch",
        "directions": [
            {
                "step": "Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
            },
            {
                "step": "Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
            },
            {
                "step": "Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
            },
            {
                "step": "Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
            }
        ],
        "categories": [
            {
                "id": 1,
                "category": "vegitarian",
                "links": [
                    {
                        "rel": "self",
                        "href": "http://localhost:8081/api/v1/categories/1"
                    },
                    {
                        "rel": "recipies",
                        "href": "http://localhost:8081/api/v1/categories/1/recipies"
                    }
                ]
            }
        ],
        "ingredients": [
            {
                "ingredient": "small capers, drained",
                "quantity": 2.0,
                "unit": "tsp"
            },
            {
                "ingredient": "flat-leaf parsley, leaves picked",
                "quantity": 1.0,
                "unit": "pack"
            },
            {
                "ingredient": "good quality sherry vinegar",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "extra virgin olive oil",
                "quantity": 150.0,
                "unit": "ml"
            },
            {
                "ingredient": "ricotta",
                "quantity": 50.0,
                "unit": "gram"
            },
            {
                "ingredient": "Dijon mustard",
                "quantity": 1.0,
                "unit": "tsp"
            },
            {
                "ingredient": "heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
                "quantity": 650.0,
                "unit": "gram"
            },
            {
                "ingredient": "garlic, finely chopped",
                "quantity": 1.0,
                "unit": "clove"
            },
            {
                "ingredient": "red onion, finely sliced",
                "quantity": 0.5,
                "unit": null
            },
            {
                "ingredient": "chervil, leaves only",
                "quantity": 1.0,
                "unit": "pack"
            },
            {
                "ingredient": "podded broad beans",
                "quantity": 100.0,
                "unit": "gram"
            }
        ],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/api/v1/recipies/1"
            }
        ]
    }
]
```

testcase 11: Create a recipe with no category
```ssh
{
	"name":"recipe3",
	"yield":1,
	"difficultyLevel":"easy",
	"preparationTime":"25 mins",
	"serves":"4",
	"cookingTime":"lunch",
    "directions":[{
		"step":"Toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside."
	},
	{
		"step":"Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs."
	},
	{
		"step":"Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells."
	},
	{
		"step":"Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve."
	}
	],
    "ingredients":[
    	{
			"ingredient":"heirloom tomatoes of different shapes and sizes, sliced and cut in different ways",
	        "quantity":650,
	        "unit":"gram"
        },
        {
			"ingredient":"red onion, finely sliced",
	        "quantity":0.5
        },
        {
			"ingredient":"podded broad beans",
	        "quantity":100,
	        "unit":"gram"   
        },
        {
			 "ingredient":"ricotta",
	         "quantity":50,
	         "unit":"gram"
	    },
        {
			"ingredient":"flat-leaf parsley, leaves picked",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"chervil, leaves only",
	        "quantity":1,
	        "unit":"pack"            
        },
        {
			"ingredient":"garlic, finely chopped",
	        "quantity":1,
	        "unit":"clove"            
        },
        {
			"ingredient":"Dijon mustard",
	        "quantity":1,
	        "unit":"tsp"
        },
        {
			"ingredient":"small capers, drained",
	        "quantity":2,
	        "unit":"tsp"            
        },
        {
			"ingredient":"extra virgin olive oil",
	        "quantity":150,
	        "unit":"ml"            
        },
        {
			"ingredient":"good quality sherry vinegar",
	        "quantity":1,
	        "unit":"tsp"
	}
    ]
}

{
    "message": "Category missing from request",
    "date": "2023-05-31"
}
```

testcase 12: create category with no ingredient
```ssh
same request with no ingredients

{
    "message": "Ingredient is missing from request",
    "date": "2023-05-31"
}
```
