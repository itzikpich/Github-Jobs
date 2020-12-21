# Github jobs Application
This is a test application build in kotlin using mvvm, showing github job posts from Github Api in a recyclerView
user can add and remove jobs to favorites, favorites are stored locally using shared preferences.
data is fetched from https://jobs.github.com/positions API using retrofit and stored locally with Room Persistence Library,
the data is then observed from database by fragments.   
using kotlin coroutines for async data fetch,
loading images using glide,

