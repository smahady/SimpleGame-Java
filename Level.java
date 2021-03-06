import java.io.*; 

public class Level implements Serializable{
	protected int xTiles;
	protected int yTiles;

	// Default constructor
	public LinkedList(){
		this.headNode = new Node("blankBB",0.00,false);
		this.finalNode = this.headNode;
		this.positionNode = this.headNode;
	}	

	// Insert a new node
	public void insert(String strNewName, Double newCost, boolean newActive){
		Node newNode = new Node(strNewName, newCost, newActive);
 		
		if (this.headNode.strName == "blankBB"){
			this.headNode = newNode;		//set newNode tot he first node if its null
			this.finalNode = newNode;		//same for last
		} else {
			this.finalNode.nextNode = newNode;	// set it to the nextNode for the lastNode
			this.finalNode = newNode;
		}

	}

	// Check the size of our linked list
	public int size(){
		Node currentNode;
		int count;

		currentNode = this.headNode;		// Start at the beginning...
		count = 0;				//This is where we'll see how many
		while (currentNode != null){
			count += 1;
			currentNode = currentNode.nextNode;	// time to move on
		}

		return count;
	}

	// Search for something. I don't tihnk I have a use for this but it could be handy.
	public Node search(String strNewName){
		int found = 0;
		Node currentNode;
	
		currentNode = this.headNode;		//Start at the beginning
		while ((currentNode != null) && (found == 0)){
			if (currentNode.strName == strNewName){
				found = 1;
			} else {
				currentNode = currentNode.nextNode;
			}
		}

		if (currentNode == null) {
			throw new NullPointerException();
		}
		
		return currentNode;
	}	

	// Deletes a node. 
	public void delete(Node newNode){

		// set up variables
		Node currentNode = this.headNode;
		Node previousNode = null;
		int found = 0;

		
		while ((currentNode != null) & (found == 0)){
			if (currentNode == newNode){
				found = 1;			//we found it!
			} else {
				previousNode = currentNode;
				currentNode = currentNode.nextNode;
			}

		}

		if (currentNode == null) {
			throw new NullPointerException();
		}
		
		if (previousNode == null) {
			this.headNode = currentNode.nextNode;
		} else {
			previousNode.nextNode = currentNode.nextNode;
			currentNode = currentNode.nextNode;
			currentNode.lastNode = previousNode;
		}
		
	}


	// Resets the lists position to head. Very important before using the algorithm to go through the getNext
	public void resetlist(){
		this.positionNode = this.headNode;
	}

	// Lets get values one at a time
	public String getNext(){
		String strName;
		Node currentNode;

		currentNode = this.positionNode;

		if (currentNode == this.finalNode){
			this.insert("New Button", 0.00, false);
		} 	

		this.positionNode = currentNode.nextNode;

		strName = this.positionNode.strName;
		return strName;
	}

	// Lets get values one at a time
	public String getLast(){
		String strName;
		Node currentNode;

		currentNode = this.positionNode;

		if (currentNode != this.headNode){
			this.positionNode = currentNode.lastNode;
		} 	

		strName = positionNode.strName;
		return strName;
	}

	// Saves a node
	public void changeNode(String newStrName, Double newCost, boolean newActive){
		this.positionNode.strName = newStrName;
		this.positionNode.Cost= newCost;
		this.positionNode.binActive = newActive;
	}
	
	// Is there another frame?
	public int isNext(){
		int returnVar;
		if (this.positionNode == this.finalNode){
			returnVar = 1;
		} else {
			returnVar = 0;
		}

		return returnVar;
	}

	// Lets get values one at a time
	public String returnNext(){
		String strName;
		Node currentNode;

		currentNode = this.positionNode;

		if (currentNode != this.finalNode){
			this.positionNode = currentNode.nextNode;
		} 	



		strName = positionNode.strName;
		return strName;
	}

	//get name without movign to next
	public String returnName(){
		String strName;
		strName = this.positionNode.strName;
		return strName;
	}

	//get cost without movign to next
	public Double returnCost(){
		Double Cost;
		Cost = this.positionNode.Cost;
		return Cost;
	}

	//get Active state without moving to next
	public boolean returnActive(){
		boolean binActive;
		binActive = this.positionNode.binActive;
		return binActive;
	}
}



