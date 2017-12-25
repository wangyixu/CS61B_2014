/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
   
    private final int width;
	private final int height;
	
	private DListNode head;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
    // Your solution here.
	this.width = width;
	this.height = height;
	head = new DListNode(new int[]{width * height, 0, 0, 0}, null, null);
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.
	this.width = width;
	this.height = height;
	head = new DListNode(new int[]{runLengths[0], red[0], green[0], blue[0]}, null, null);
	
	DListNode tempNode = head;
	for (int i = 1; i < runLengths.length; i++) {
		tempNode.next = new DListNode(new int[]{runLengths[i], red[i], green[i], blue[i]}, tempNode, null);
		tempNode = tempNode.next;
	}
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
    return new RunIterator(head);
    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.2
   */
  public PixImage toPixImage() {
    // Replace the following line with your solution.	
	PixImage pixImage = new PixImage(width, height);
	DListNode currentNode = head;
	int x = 0, y = 0;
	while (currentNode != null) {
		int[] info = currentNode.info;
		for (int i = 0; i < info[0]; i++) {
			pixImage.setPixel(x, y, (short)info[1], (short)info[2], (short)info[3]);
			x++;
			if (x == width) {
				y++;
				x = 0;
			}
		}
		currentNode = currentNode.next;
	}
    return pixImage;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
    // Replace the following line with your solution.
	DListNode currentNode = head;
	while (currentNode != null) {
		int[] info = currentNode.info;
		//System.out.println("Run'length is " + info[0] + ",RGB is: " + info[1] + "," + info[2]  + "," + info[3]);
		currentNode = currentNode.next;
	}
    return "nothing";
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
    // Your solution here, but you should probably leave the following line   RunLengthEncoding
    // at the end.	
	this.width = image.getWidth();
	this.height = image.getHeight();		
	//一开始弄了个info数组保存每个DListNode的信息，加以引用，最后发现所有DListNode的值是一样的了，调了好久。
	//引用和值的不同
	int count = 0;
	int[] info = new int[]{0, image.getRed(0, 0), image.getGreen(0, 0), image.getBlue(0, 0)};
	DListNode currentNode = new DListNode(info, null, null);
	for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
			
			if (image.getRed(j, i) == info[1] && image.getGreen(j, i) == info[2] && image.getBlue(j, i) == info[3]) {
				count++;
			} else {
				if (head == null) {
					head = new DListNode(new int[]{count, info[1], info[2], info[3]}, null, null);
					currentNode = head;
				} else {					
					currentNode.next = new DListNode(new int[]{count, info[1], info[2], info[3]}, currentNode, null);
					currentNode = currentNode.next;
				}
				count = 1;
				info[1] = image.getRed(j, i);
				info[2] = image.getGreen(j, i);
				info[3] = image.getBlue(j, i);
			}
			
			if (i == height - 1 && j == width - 1) {      //最后一个1.与上一个相同，count++了，新建；2.不同，新建。
				currentNode.next = new DListNode(new int[]{count, info[1], info[2], info[3]}, currentNode, null);
			}
		}
	}
	check();
  }
  
  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.      RunLengthEncoding
   */
  public void check() {
    // Your solution here.
	DListNode currentNode = head, 
		nextNode = head.next;
	int runCount =  head.info[0];
	//System.out.println(runCount);
	
	while (nextNode != null) {
		int[] curInfo = currentNode.info,
			nextInfo = nextNode.info;
			
		if (curInfo[1] == nextInfo[1] && curInfo[2] == nextInfo[2] && curInfo[3] == nextInfo[3]) {
			System.out.println("Error! Same run contents.");
		}
		if (curInfo[0] < 1 || nextInfo[0] < 1) {
			System.out.println("Error! Run length is less than 1.");
		}		
		runCount += nextInfo[0];
		//System.out.println(nextInfo[0]);
		currentNode = nextNode;
		nextNode = nextNode.next;
	}
	if (runCount != width * height) {
		System.out.println("Error! Run length is not equal to image's length.");
	}
  }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here, but you should probably leave the following line  
    //   at the end.
	/*Map<Integer, DListNode> position = findPosition(x, y);              //不让用Java类包。。。。。。。。。。。。。。。。。。。。
	Set<Integer> keys = position.keySet() ;    // 得到全部的key     RunLengthEncoding
	Iterator<Integer> iter = keys.iterator() ;
	while (iter.hasNext()) {
		String str = iter.next();
		int index = iter.next().intValue();
	}
	DListNode curNode = position.getValue(index); */
	
	DListNode curNode = findNode(x, y);
	int index = findIndex(x, y);
	int[] info = curNode.info;
	if ((short)info[1] == red && (short)info[2] == green && (short)info[3] == blue) return;
	
	if (index == 0) {
		if (info[0] == 1) { //1.两边
			DListNode prevNode = curNode.prev;   //maybe null
			DListNode nextNode = curNode.next;   //maybe null
			
			if (prevNode != null && ((short)prevNode.info[1] == red && (short)prevNode.info[2] == green && (short)prevNode.info[3] == blue)) {  
				if (nextNode != null && ((short)nextNode.info[1] == red && (short)nextNode.info[2] == green && (short)nextNode.info[3] == blue)) {    //1.1两边并，改变前一个Node的run，删去此、后Node
					prevNode.info[0] += nextNode.info[0] + 1;
					prevNode.next = nextNode.next;
					nextNode.next.prev = prevNode;
				} else {        //1.2左并，前一个Node的run +1，删去此Node
					prevNode.info[0]++;
					prevNode.next = curNode.next;
					if (nextNode != null) {
						nextNode.prev = prevNode;
					}
				}
			} else if (nextNode != null && ((short)nextNode.info[1] == red && (short)nextNode.info[2] == green && (short)nextNode.info[3] == blue)) {    //1.2右并
				nextNode.info[0]++;
				if (prevNode != null) {
					nextNode.prev = prevNode;
					prevNode.next = nextNode;
				} else {
					head = nextNode;
					head.prev = null;
				}
			} else {   //1.3改变此Node
				curNode.info[1] = red;
				curNode.info[2] = green;
				curNode.info[3] = blue;
			}
		} else {  //2.左边
			DListNode prevNode = curNode.prev;
			if (prevNode != null && ((short)prevNode.info[1] == red && (short)prevNode.info[2] == green && (short)prevNode.info[3] == blue)) {   //1.1左并，前一个Node的run +1，删去此Node
				prevNode.info[0]++;
				curNode.info[0]--;
			} else { //2.2左分，此Node的run -1，前加Node
				curNode.info[0]--;
				curNode.prev = new DListNode(new int[]{1, red, green, blue}, null, curNode);
				if (prevNode == null)  {
					head = curNode.prev;
				} else {
					curNode.prev.prev = prevNode;
					prevNode.next = curNode.prev;
				}
			} 
		}
	} else if (index == info[0] - 1) {  //3.右边
		DListNode nextNode = curNode.next;
		if (nextNode != null && ((short)nextNode.info[1] == red && (short)nextNode.info[2] == green && (short)nextNode.info[3] == blue)) { //3.1右并，此、后Node的run 进行 +/- 1；
			nextNode.info[0]++;
			curNode.info[0]--;
		} else { //3.2右分，此Node的run -1，后加Node
			curNode.info[0]--;
			curNode.next = new DListNode(new int[]{1, red, green, blue}, curNode, null);
			if (nextNode != null) {
				curNode.next.next = nextNode;
				nextNode.prev = curNode.next;
			}
		} 
	} else {  //4.中间，原Node后插入2个Node。
		int nums1 = index;
		int nums2 = info[0] - index - 1;
		
		curNode.info[0] = nums1;		
		DListNode temp = curNode.next;
		curNode.next = new DListNode(new int[]{1, red, green, blue}, curNode, null);
		curNode.next.next = new DListNode(new int[]{nums2, info[1], info[2], info[3]}, curNode.next, null);
		curNode.next.next.next = temp;
		temp.prev = curNode.next.next;
	}	
    check();
  }
  
	private DListNode findNode(int xFind, int yFind) {
		
		DListNode currentNode = head;
		int x = 0, y = 0;
		while (currentNode != null) {
			int run = currentNode.info[0];
			for (int i = 0; i < run; i++) {
				if (x == xFind && y == yFind) {
					return currentNode;
				}
				if (++x == width) {
					y++;
					x = 0;
				}
			}
			currentNode = currentNode.next;
		}
		return currentNode;
	}
	private int findIndex(int xFind, int yFind) {
		
		DListNode currentNode = head;
		int x = 0, y = 0;
		while (currentNode != null) {
			int run = currentNode.info[0];
			for (int i = 0; i < run; i++) {
				if (x == xFind && y == yFind) {
					return i;
				}
				if (++x == width) {
					y++;
					x = 0;
				}
			}
			currentNode = currentNode.next;
		}
		return -1;
	}

  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }
  
  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });
												   
    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
	System.out.println(image1);
	System.out.println(rle1.toPixImage());
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");

    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 0, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
