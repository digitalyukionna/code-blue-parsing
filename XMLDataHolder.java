import java.util.*;

public class XMLDataHolder {
	String element;
	String data;
	ArrayList<XMLDataHolder> subData = new ArrayList<XMLDataHolder>();

	/*
	 * reads data into the holder. Finds the first element within the list and
	 * matches it to the terminal element. If, between those elements, there is
	 * a single data field, it sets that as data and returns true. If not, it
	 * grabs the next available element, locates the next terminal element, and
	 * passes all of that data into an ArrayList which is then fed into a new
	 * DataHolder, which is added into the subData arrayList. If the return from
	 * that is true, it then grabs the next available element and its terminal
	 * and does this test again. If it reaches the end of the List it's been
	 * passed, it returns true. If an error occurs, it returns false.
	 */
	public boolean dataIn(ArrayList<String> sList) {
		thisElement(sList.get(0));
		if (containsSubElements(sList) == false){//That is, there's only a single data field inside
			//this automatically assigns the data as well.
			//If there's no sub-elements, then there's no other data here.
			return true;
		}
		else {//there's more elements in there!
			/*
			 * for every element pair beneath this one
			 * it loads a sub-arrayList and makes a new
			 * XMLDataHolder.
			 */
			ArrayList<String> subList= new ArrayList<String>();
			String terminalElement;// = "</" + getElement(sList.get(0));
			//Get where the terminal element is.
			//Put the intervening lines into sublist
			//Make a new XMLDataHolder and put it into the subData list.
			//if that.dataIn(subList) == false, return false because error.
			//else clear the lines up to the terminal element in sList.
			//clear the terminal element in now sList.get(0);
			//clear the subList
			//Do it until sList is empty.
			sList.remove(0);//Remove this element!  Else it recurses endlessly.
			
			while (!(sList.isEmpty())){
				
				terminalElement = "</" + getElement(sList.get(0));
				int terminalLocation=-1;
				for (int counter = 0; counter<sList.size();counter++){
					if (sList.get(counter).contains(terminalElement)){
						terminalLocation = counter;
						counter = sList.size();
					}
				}
				if (terminalLocation == -1){//Error!  We can't find a matching terminator.
					terminalLocation =sList.size(); //We'll assume that the final closure is missing for some reason.
				}
				for (int counter = 0; counter<terminalLocation;counter++){
					subList.add(sList.get(counter));
				}
				for (int counter = 0; counter<terminalLocation;counter++){
					//System.out.println(sList.get(0));
					//remove from sList.
					sList.remove(0);
				}
				subList.add(terminalElement+">");
				if (sList.size() >1){ //If it's only one there's only the closing bracket left.
					String tempString = sList.get(0);
					sList.remove(0);
					sList.set(0, tempString.substring(terminalElement.length()));
				}
				terminalLocation = -1;
				subData.add(new XMLDataHolder());
				if(subData.get(subData.size()-1).dataIn(subList)==false){
					return false; //We hit a problem!
				}
			}
			return true;
			
		}
	}
	/*
	 * Slightly redundant; this only gets the element inside of the string
	 * that it's handed.  
	 */
	public String getElement(String s){
		String temp = "";
		System.out.println(s);
		temp = s.substring(1, s.indexOf(">"));
		System.out.println(temp);
		if (temp.contains(" ")){
			return temp.substring(0, temp.indexOf(" "));
		}
		return temp;
	}
	/*
	 * First element takes the string, grabs the data between the angle
	 * brackets, and sets that as this Holder's element. Can set the data if
	 * it's part of the
	 */
	private void thisElement(String s) {
		String temp = s.substring(1, s.indexOf(">"));
		if (temp.contains(" ")) {// That is, if there's a whitespace inside
									// there's data there.
			element = temp.substring(0, temp.indexOf(" "));
			data = temp.substring(temp.indexOf(" "));
		} else {
			element = temp;

		}
	}

	private boolean containsSubElements(ArrayList<String> sList) {
		System.out.println(element);
		String closingElement = "</" + element + ">";
		int closingEndLine = getEndLine(sList, closingElement);
		if (closingEndLine == 0) { // That is, there's no sub elements
			assignData(sList.get(0));
			return false;
		} else if (closingEndLine == -1) { // That is, there's a problem.
			return true; // We'll assume that the close is wrong for some reason.
		}
		return true;

	}

	/*
	 * assigns the data to this element. Can be empty. This assumes that data
	 * wasn't assigned during the element step, which can happen in certain
	 * cases. Example: <order id="1234">
	 */
	private void assignData(String s) {
		String temp = s.substring(1, s.length() - 1);// remove the external <
														// and >
		int dataStart = temp.indexOf(">") + 1;
		int dataEnd = temp.lastIndexOf("<");
		if (dataStart == dataEnd && data == null) {// data is null if the data
													// wasn't assigned earlier.
			data = "";// That is, there's nothing there.
		} else {
			data = temp.substring(dataStart, dataEnd);
		}
	}

	/*
	 * Gets the location within the list of the terminal element. if it can't
	 * find it, it returns -1.
	 */
	private int getEndLine(ArrayList<String> sList, String terminalElement) {
		for (int counter = 0; counter < sList.size(); counter++) {
			if (sList.get(counter).contains(terminalElement)) {
				return counter;
			}
		}
		return -1;
	}
}
