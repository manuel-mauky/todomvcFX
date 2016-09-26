# TodoMVC JavaFX

[![Build Status](https://travis-ci.org/lestard/todomvcFX/.svg?branch=master)](https://travis-ci.org/lestard/todomvcFX)

TodoMVC-JavaFX is a collection of different implementations of the same JavaFX application. It aims to provide examples with different design patterns, frameworks, programming languages and programming styles. 

It's inspired by the famous [TodoMVC](http://todomvc.com/) project which is doing the same thing for JavaScript/Web frameworks. 

By implementing the same use cases with different approaches we hope to provide several benefits:

- find out which different approaches and frameworks are out there
- compare different frameworks
- learn how to setup a small application with a specific framework and use it as basic project skeleton
- bring JavaFX community together and discuss the various approaches


## Contribute

**Contributions are very welcome**.
There are several thinks to help out:

- add a new example for another framework/language/pattern
- improve existing examples
- improve documentation of the examples
- ideas on how the build scripts can be improved
- fix spelling errors
- ...


### How to add a new example?

1. Add a new issue with your idea. This way we can discuss about your idea and can make sure that multiple people aren't working on the same things without knowing from each other
2. Fork the repository
3. Add a new gradle module for your example
  - Create a directory `examples/<your-framework>`
  - Add the module in `settings.gradle`
  - Add a `build.gradle` file in your directory for your submodule
4. Implement your example app. 
5. Add an acceptance test for your app. See [Acceptance Tests](https://github.com/lestard/todomvcFX#acceptance-tests) for more infomations. If this approach doesn't work for your framework or language please comment in your issue and we will find a solution.
6. Create a pull request

The module `reference_impl` contains a basic solution of how the app should work. You can use this as a starting point for your app. There are also FXML and CSS files that you can use.


### Acceptance Tests

The idea behind this project is to have multiple implementations of the same use cases. To make sure that each implementation has the same behaviour and acomplishes the same requirements there is a test suite of [TestFX](https://github.com/TestFX/TestFX) test cases for all requirements. You can find the tests in [/tests/src/main/java/todomvcfx/AbstractTest.java](https://github.com/lestard/todomvcFX/blob/master/tests/src/main/java/todomvcfx/AbstractTest.java). 

To add the acceptance tests for your module:

1. Create a test class in your module with a name like `<your-framework>Test`
2. This test class has to extend from `todomvcfx.AbstractTest` (the dependency to the `tests` module is defined globally in the root `build.gradle` file)
3. Override the method `getAppClass()` in your test class. This method has to return a class reference to your application's starter class (the class that extends from `javafx.application.Application).

