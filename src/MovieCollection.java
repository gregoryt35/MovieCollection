import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)ighest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by keyword
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
      System.out.print("Enter a person to search for (first or last name): ");
      String searchTerm = scanner.nextLine();

      searchTerm = searchTerm.toLowerCase();

      ArrayList<String> results = new ArrayList<>();

      for (int i = 0; i < movies.size(); i++) {
        String castStringOfMovie = movies.get(i).getCast();
        castStringOfMovie = castStringOfMovie.toLowerCase();
        String totalCast = "";
        totalCast += castStringOfMovie;

        String[] castList = totalCast.split("\\|");

        for (String str : castList) {
            if (str.indexOf(searchTerm) != -1) {
                if (results.indexOf(str) == -1) {
                    results.add(str);
                }
            }
        }

      }

      Collections.sort(results);

      for (int i = 0; i < results.size(); i++) {
        String actor = results.get(i);

        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + actor);
      }

      System.out.println("Which actor would you like to learn more about?");
      System.out.print("Enter a number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      ArrayList<Movie> movieList = new ArrayList<>();

      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getCast().toLowerCase().contains(results.get(choice - 1))) {
          movieList.add(movies.get(i));
        }
      }

      sortResults(movieList);

      for (int i = 0; i < movieList.size(); i++) {
          String movieTitle = movieList.get(i).getTitle();

          int choiceNum = i + 1;

          System.out.println("" + choiceNum + ". " + movieTitle);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      choice = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = movieList.get(choice - 1);

      displayMovieInfo(selectedMovie);

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeywords = movies.get(i).getKeywords();
      movieKeywords = movieKeywords.toLowerCase();

      if (movieKeywords.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String keyword = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + keyword);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
      ArrayList<String> results = new ArrayList<>();

      for (int i = 0; i < movies.size(); i++) {
          String genres = movies.get(i).getGenres();
          String allGenres = "";
          allGenres += genres;

          String[] genreList = allGenres.split("\\|");

          for (String str : genreList) {
              if (results.indexOf(str) == -1) {
                  results.add(str);
              }
          }
      }

      Collections.sort(results);

      for (int i = 0; i < results.size(); i++) {
          String genre = results.get(i);

          int choiceNum = i + 1;

          System.out.println("" + choiceNum + ". " + genre);
      }

      System.out.println("Which genre would you like to learn more about?");
      System.out.print("Enter number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      ArrayList<Movie> movieList = new ArrayList<>();

      for (int i = 0; i < movies.size(); i++) {
          if (movies.get(i).getGenres().contains(results.get(choice - 1))) {
              movieList.add(movies.get(i));
          }
      }

      sortResults(movieList);

      for (int i = 0; i < movieList.size(); i++) {
          String movieTitle = movieList.get(i).getTitle();

          int choiceNum = i + 1;

          System.out.println("" + choiceNum + ". " + movieTitle);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      choice = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = movieList.get(choice - 1);

      displayMovieInfo(selectedMovie);

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();

  }
  
  private void listHighestRated()
  {
    /* TASK 6: IMPLEMENT ME! */
  }
  
  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
  }

  private void importMovieList(String fileName)
  {
      try
      {
          FileReader fileReader = new FileReader(fileName);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String line = bufferedReader.readLine();

          movies = new ArrayList<Movie>();

          while ((line = bufferedReader.readLine()) != null)
          {
              String[] movieFromCSV = line.split(",");

              String title = movieFromCSV[0];
              String cast = movieFromCSV[1];
              String director = movieFromCSV[2];
              String tagline = movieFromCSV[3];
              String keywords = movieFromCSV[4];
              String overview = movieFromCSV[5];
              int runtime = Integer.parseInt(movieFromCSV[6]);
              String genres = movieFromCSV[7];
              double userRating = Double.parseDouble(movieFromCSV[8]);
              int year = Integer.parseInt(movieFromCSV[9]);
              int revenue = Integer.parseInt(movieFromCSV[10]);

              Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
              movies.add(nextMovie);
          }
          bufferedReader.close();
      }
      catch(IOException exception)
      {
          System.out.println("Unable to access " + exception.getMessage());
      }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}