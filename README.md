# PrestoQ


## Introduction: 

This is my solution to the PrestoQ Android assignment (as specified here: https://github.com/prestoqinc/code-exercise-android). 

The master branch includes everything you’ll need to run the app, including the commit history and unit tests.

    git clone https://github.com/oudavid/PrestoQ


## Process: 

I began the assignment by breaking down the requirements and putting them into user stories. 

#### Setup
- Create the project
- Add all necessary dependencies and use AndroidX
- Create data model classes 
- Decide on design pattern

#### As a user, I want to see an updated list of products from the Manager’s Specials. 
- Products must be displayed in the order that they are provided
- Update products whenever Manager’s Specials is updated
- When Manager’s Specials is empty, show message
- When there is no network, show message

#### As a user, I want the list of product layout to be organized according to the endpoint specifications.
- No product frame will have a width greater than the canvasUnit, throw exception if this 
- Display as many products as possible while still following sizing parameters
- If the combined width of the product frames exceeds the full width of the phone move the product frame to the next line
- Center any frames that do not fit in the full width frame.
- The size of each product tile must be the size specified by the endpoint (See "Endpoint" for more information about sizing)

## Libraries

These are some of the libraries that I used to complete this assignment.

#### Retrofit
I’m using the Retrofit library built by Square, because it is a great type-safe REST client for Android. Retrofit makes it easy to consume JSON data which is parsed into Kotlin objects. 

#### Glide 
I also decided to use Glide because it’s another best practice library. Glide is a fast and efficient image loading library that uses a LRU cache. When the disk cache is full, the least recently used image gets removed. This is important when needing to handle many high resolution images 

#### FlexBox Layout
Google’s FlexBox layout is also efficient on memory because it can be used with RecyclerView. Instead of inflating every individual view, it recycles the views that are no longer visible on the screen so that they can be reused as the user scrolls. More importantly, this layout makes it easy to wrap items onto the same row if there is space available (without complex logic to manually position items). 

## Design Pattern: 

I decide to go with the MVVM design pattern for the following reasons: 


#### Separation of concerns 
  Compared to other patterns, MVVM design pattern presents a better separation of concerns by using view models. The view model translates the data of the model layer into something the view layer can use. Business logic is no longer tied to the view.


#### Improved testability 
  Removing the business logic from the Activity/Fragment improves the testability of the code. Android-specific classes are not tied with the business logic, so these can be isolated and unit tested easily. 


#### Clear responsibilities
  The view is responsible for showing UI elements and responding to user input. The model represents the data from the rest service. And the view model is the mediator between the view and the data model.
Conclusion 

Thanks for taking the time to look at my assignment!

P.S. Here's what the app should look like:

![Here's what the app should look like](https://i.imgur.com/D0RamOy.png)
