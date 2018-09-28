Design Document- CPEN 221- MP#1

Document Questions:

#You will need to complete the implementation of the Document class. How should one represent a Document? By representation, we refer to the private data that you will want to store in a Document object. For example, we represent a Triangle using the x-y coordinates of its corners. The choice here should be sufficient for solving Tasks 1-3, and should avoid having to perform redundant operations.
*Document Constructor(initialization)
	*String Url: the url at which to find the work
	*String ID: the ID of the work (also the question represents the ID of the work (CAN USE ANYTHING TO REPRESENT IDs, ex. NUMBERS, LETTERS)
		*Url can be used as ID
	*StringBuilder Text: the entirety of the text
	*ArrayList String
		*Chopped up text segments
			*To get chopped off segment: go directly to index 5000 (indexMax) then back up index until we hit a blank/space
	*Hashmap<String, Integer> wordMap: hashmap of all the different words (key) and integer is how many times the word appears (value)
	*totalWords: integer variable to store total number of words in a document
	*docScan: Scanner
		*Local to constructor (every Document object doesn't have to hold a scanner object)
	*int sentimentScore: median of each piece sent, for each document
	*Consider overloading the constructor to take in a string instead of URL, to test custom input

#What tasks should be performed when a new Document object is created? (Example: store the URL for the document in the field url.)
*Overall design choice: Everything that can be setup in the constructor is set up in the constructor with the exception of the sub-chunks for checks (by this we mean the strings that individually get tested for sentiment)
	*Store the URL to url
	*Instantiate the scanner to read from the website at url
	*Use scanner to read the text at the website word by word:
		*If the word isn’t already held in the map, add the word as key with value one. Else, increase the value associated with the word by one
		*Increment totalWords by 1 at each new word
	*Chop it into substrings
		*Add each new word to stringbuilder, fill ArrayList of texts (to be sent to Azure)

#How do you plan to implement the method to find the most similar documents? Provide a high-level description of your algorithm in English
	*First we will create each document by representing them with the attributes mentioned in the Document Constructor. Additionally, while going through the url we will count all the words in the document to know the total. Then we will read from the string and store each unique word as a key in a hashmap and each key’s value will be the number of times that word appears in the document. This will be done for two documents so that we can compare them. 
	*Then we will use the Jensen-Shannon Divergence to calculate the similarity of both documents in terms of their words. This will be done using the values in the map (the number of times a word appears) and the total count of words, as we can calculate the probability of the word in each document, which is necessary for the Jensen-Shannon Divergence.
	*With a nested loop, we can examine every pair of Documents, and always save the lowest score, and the documents associated with it.
	*The documents we have saved once we have gone through every pair of Documents will be the ones with the least difference (i.e. most similar)

#How will you test the correctness of your work? What are some test cases that you will use to validate your implementation? List four different test cases that cover different scenarios for finding the most similar documents

Scenario being tested:						Test case:

JSD: Word appears in both 					compute term for “the”

JSD: Word appears in one but not other				“Pemberly”, when comparing Pride and Prejudice and Emma

Sentiment: similar pairs returned correctly			no documents, 1 document, 2 documents							

GroupSimilarDocuments function					no documents, 1 document, 2 documents

***************

When compared to our original design document, the changes we did are as follows:

*In the Document Constructor
	*While forming the texts we will send to Azure, instead of starting from index 5000 and counting back as stated in our design document, we started from index 0 and incremented up to the point we could add the last word to the text.
	*Instead of storing the sub-texts that will be sent to Azure in an ArrayList as we initially thought, we added each text to the DataCollection object called "request", after we figured that data type.
	*A major change we did in terms of our Document constructor is that we did not store the whole text of the document as a string (or stringbuilder). We formed the sub-texts that make up the request that will be sent to Azure in the constructor, and since we did not need to read through the entire text again in another method, we didn't create a field for storing the whole text of the document.
*The minimum divergence is initially given the value of 100 as opposed to 1 as stated in our design document.
*Testing Methods (while developing our implementation, we tested the following cases respectively)
	_The following tests were done during the implementation of the methods and were printed out to check the correctness of our implementations._
	_The methods were then completed to pass the given tests and the additional tests. Therefore, although all the test cases mentioned below cannot be found in our test class, they were done during the development of the code.
	*computing the Jensen-Shannon Divergence for only one word, for a word which occurs in only one of the documents, for two identical documents, for one document, and for no documents. 
	*computing the median with even, odd numbered inputs as well as with size 0 and 1 which are the edge cases. 
	*grouping documents according to their divergence scores with no documents, one document, and two documents with varying numGroups values.
