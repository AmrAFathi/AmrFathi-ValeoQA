 Feature: Test search function
   Scenario: Check user can navigate to the search engine and search with the key "Cars in London"
     Given User opens the browser and navigates to the search engine website
     When User types the search key "Cars in London" and clicks on the "Search" button
     Then The search engine finds all related results including results with ads

