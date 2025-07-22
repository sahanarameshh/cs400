import java.util.List;

public class Frontend implements FrontendInterface{
    
  private Backend_Placeholder backend;
  
  /**
     * Implementing classes should support the constructor below.
     * @param backend is used for shortest path computations
     */
    public Frontend(Backend_Placeholder backend){
      this.backend = backend;
    }
    
    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a shortest path computation
     */
    public String generateShortestPathPromptHTML(){
      return "<input type=\"text\" id=\"start\" placeholder=\"From\">" +
               "<input type=\"text\" id=\"end\" placeholder=\"To\">" +
               "<button>Find Shortest Path</button>";
    }
    
    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate 
     * the kind of problem encountered.
     * @param start is the starting location to find a shortest path from
     * @param end is the destination that this shortest path should end at
     * @return an HTML string that describes the shortest path between these
     *         two locations
     */
    public String generateShortestPathResponseHTML(String start, String end){
      try{
        List<String> locations = backend.findLocationsOnShortestPath(start, end);
        List<Double> times = backend.findTimesOnShortestPath(start, end);
        if(times.isEmpty()){
          return "<p>There are no path from " + start + " to " + end + ".</p>";
        }
        StringBuilder html = new StringBuilder();
        html.append("<p>You are going from ").append(start).append(" to ").append(end).append(".</p>");
        html.append("<ol>");
        for (String loc : locations) {
            html.append("<li>").append(loc).append("</li>");
        }
        html.append("</ol>");
        html.append("<p>Total travel time: ").append(times.get(times.size()-1)).append(" seconds.</p>"); //will the index alwyas be 0?

        return html.toString();        
      }catch(Exception e){
        return "<p> Error occur. </p>";
      }
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="from", for the start locations
     * - a text input field with the id="time", for the max time limit
     * - a button labelled "Reachable From Within" to submit this request
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a ten closest destinations calculation
     */
    public String generateReachableFromWithinPromptHTML(){
      return "<input type = \"text\" id = \"start\" placeholder = \"From\">" +
               "<input type = \"text\" id = \"time\" placeholder = \"Max time\">" +
               "<button>Reachable From Within</button>";
    }
    
    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) describing the start location and travel time allowed
     * - an unordered list (ul) of destinations that can be reached within
     *        that allowed travel time
     * Or if no such destinations can be found, the HTML returned should 
     * instead indicate the kind of problem encountered.
     * @param start is the starting location to search from
     * @param travelTime is the maximum number of seconds away from the start
     *        that will allow a destination to be reported
     * @return an HTML string that describes the closest destinations from the
     *         specified start location.
     */    
    public String generateReachableFromWithinResponseHTML(String start, double travelTime){
      try{
        List<String> locations = backend.getReachableFromWithin(start, travelTime);
        if(locations.isEmpty()){
          return "<p>There are no closest destinations that you can go within " + travelTime + " seconds.</p>";
        }
        StringBuilder html = new StringBuilder();
        html.append("<p>You are walking ").append(travelTime).append(" seconds from ").append(start).append(".</p>");
        html.append("<ul>You can go to ");
        for (String loc : locations) {
            html.append("<li>").append(loc).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
      }catch(Exception e){
        return "Error occur.";
      }
    }
}