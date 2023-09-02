package AdmissionProcess;
import java.util.Scanner;

class studentNode{
	long SeatNum;
	studentNode next;
	float Mathspercentile;
	float Chempercentile;
	float Physicspercentile;
	String studentName;
	double studentPercentile;
	String studentCity;
	double studentpayment;
	int fee;

	studentNode(long SN, String N, float M, float C, float P, double Mark, String studentCity){
		next = null;
		SeatNum = SN;
		Mathspercentile = M;
		Chempercentile = C;
		Physicspercentile = P;
		studentName = N;
		studentPercentile = Mark ;
		this.studentCity = studentCity;
		studentpayment = 0;
		fee = 0;
	}
}

class Admission {
	studentNode front , rear;
	//here count is total number of applications college
	int count ;
	studentNode SelectedStudentHead;

	Admission(){
		front = null;
		rear = null;
		int count =0;
		SelectedStudentHead = null;
	}

	void studentAccept() {
		Scanner sc= new Scanner(System.in);
		Scanner sr= new Scanner(System.in);
		System.out.println("Enter total number of applications : ");
		int n = sc.nextInt();

		for(int i=0;i<n;i++) {
			System.out.println("\nEnter Exam Seat Number:");
			long SN=sr.nextLong();
			sr.nextLine();

			System.out.println("Enter student Name: ");
			String N = sr.nextLine();

			System.out.println("Enter student Maths percentile: ");
			float M = sc.nextFloat();

			System.out.println("Enter chemistry percentile: ");
			float C = sc.nextFloat();

			System.out.println("Enter Physics percentile: ");
			float P = sc.nextFloat();

			System.out.println("Enter total percentile: ");
			float percentile = sc.nextFloat();

			System.out.println("Enter student City: ");
			String city = sr.nextLine();

			studentNode temp = new studentNode( SN, N, M, C, P, percentile, city);
			if(front==null) {
				front = temp;
				rear = temp;
			}
			else {
				rear.next = temp;
				rear = temp;
			}

		}
	}

	void Print() {
		System.out.println("Names of students who applied  :");
		studentNode ptr = front;

		while(ptr!=null) {
			System.out.println(ptr.studentName);
			ptr = ptr.next;
		}
	}

	studentNode findMiddleNode(studentNode front)
	{
		if(front==null)
			return front;
		studentNode slow =  front, fast = front;

		while(fast.next!= null&& fast.next.next!=null){
			slow =  slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	studentNode MergeSort( studentNode N){
		if (N==null || N.next==null){
			return N;
		}
		studentNode middle = findMiddleNode(N);
		studentNode nextofmiddle = middle.next;
		middle.next = null;
		studentNode left = MergeSort(N);
		studentNode right = MergeSort(nextofmiddle);
		return Merge(left,right);

	}


	studentNode Merge(studentNode left, studentNode right){
		if(left==null)
			return right;

		if(right==null)
			return left;
		studentNode result;

		if(left.studentPercentile>=right.studentPercentile){
			result = left;
			result.next = Merge(left.next,right);
		}
		else{
			result = right;
			result.next =  Merge(left, right.next);
		}
		return result;
	}

	void SelectedApplicant(){
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter number of applicants that you want to select : ");
		int n = sc.nextInt();
		sc.nextLine();

		for(int i=0;i<n;i++) {
			
			if(count<=10) {
				studentNode ptr = front;
				front = front.next;
				count = count + 1;
				ptr.next= null;
				System.out.println(ptr.studentName + " has been selected.");
				System.out.println("Fees paid at the time of Admission : (of " + ptr.studentName+")");
				ptr.fee = sc.nextInt();
				sc.nextLine();

				if(SelectedStudentHead==null){
					SelectedStudentHead = ptr;
				}

				else{

					studentNode temp =  SelectedStudentHead ;
					while(temp.next!=null){
						temp = temp.next;
					}
					temp.next = ptr;
				}
			}

			else {
				System.out.println("Admission is full!!!");
			}
		}	
	}




	void CancelApplication(){
		count = count - 1;
		Scanner sr= new Scanner(System.in);
		System.out.println("Enter Exam Seat Number of the applicant whose admission is to be deleted : ");
		long SeatNo = sr.nextLong();
		sr.nextLine();

		studentNode prev = null;
		studentNode ptr = SelectedStudentHead;

		if(ptr!=null && ptr.SeatNum== SeatNo){
			SelectedStudentHead = ptr.next;
			return;
		}

		while(ptr!=null&& SeatNo!= ptr.SeatNum){
			prev = ptr;
			ptr = ptr.next;
		}

		if(ptr == null){
			return;
		}

		prev.next = ptr.next;
		System.out.println("Admission of Applicant "+ptr.studentName+" has been cancelled.");
	}

	void fee_payment(){
		Scanner sc = new Scanner(System.in);    
		System.out.println("Enter Exam seat number of the applicant whose fees have to pay : ");
		long N = sc.nextLong();
		studentNode ptr = SelectedStudentHead;

		while(ptr!=null){
			if(N==ptr.SeatNum){
				if(ptr.fee<100000){
					System.out.println("Balance fee to be paid : " + (100000 - ptr.fee) );
					int BalFee = sc.nextInt();
					ptr.fee = ptr.fee + BalFee;

					if(ptr.fee==100000) {
						System.out.println("Full Fee Paid !!!");
						break;
					}
					break;
				}
			}
			ptr = ptr.next;
		}
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Admission obj = new  Admission();
		int ch;

		do {
			System.out.println("=====*****DISPLAY MENU*****=====");
			System.out.println("\n1. ACCEPT STUDENT DETAILS");
			System.out.println("\n2. PRINT SORTED STUDENT LIST");
			System.out.println("\n3. SELECTED APPLICANT");
			System.out.println("\n4. FEE PAYMENT");
			System.out.println("\n5. CANCEL STUDENT APPLICATION");
			System.out.println("========********========");
			System.out.println("\nENTER YOUR CHOICE : ");
			int choice = sc.nextInt();

			switch(choice) {

			case 1:
				obj.studentAccept();
				break;

			case 2:
				obj.front  = obj.MergeSort(obj.front);
				obj.Print();
				break;

			case 3:
				obj.SelectedApplicant();
				break;

			case 4:
				obj.fee_payment();
				break;

			case 5:
				obj.CancelApplication();
				break;
			default:
				System.out.println("invalid");
				break;
			}

			System.out.println("\nIF YOU WANT TO CONTINUE PRESS 1 AND 0 TO EXIT");
			ch = sc.nextInt();
			sc.nextLine();

		}while(ch != 0);
	}
}
