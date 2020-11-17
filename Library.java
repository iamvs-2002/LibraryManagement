/*
Author: Vaibhav Singhal
*/

import java.util.*;

public class Library {

    public static void main(String[] args)
    {
        System.out.print("Enter the number of books: ");//taking the input from the user
        int totalbooks;//stores the total number of books initially in the library
        Scanner sc=new Scanner(System.in);
        totalbooks=sc.nextInt();
        Book books=new Book();
        int i=0;

        while(i<totalbooks)
        {
            System.out.print("Enter Book Id: ");//here we will store details of each book by the user first
            int book_id=sc.nextInt();
            System.out.print("Enter quantity: ");//quantity of each book
            int qty=sc.nextInt();
            books.book_id[i]=book_id;
            books.quantity[i]=qty;
            i++;
        }

        System.out.print("Enter number of members: ");//the total number of members
        int totalnumberofmembers=sc.nextInt();
        Member member=new Member();


        i=0;
        while(i<totalnumberofmembers)
        {
            System.out.print("Enter member id: ");//details of each and every member
            int member_id=sc.nextInt();
            member.memberID[i]=member_id;
            member.bill[i]=100;
            i++;
        }
        Date date=new Date();//object date of class Date has been created using new
        //storing individual member date of issue is they borrowed book

        int quit=0;
        while(quit!=1)
        {
            System.out.println("Choose one from the following:");
            System.out.println("1. Issue book");
            System.out.println("2. Return book");
            System.out.println("3. Fetch bill");
            System.out.println("4. Exit");
            int choice=sc.nextInt();
            switch (choice)
            {
                case 1:member.issue(books,date);//issue function in member class has been called
                    break;
                case 2:member.rtn(books,date);//return function in member class has been called
                    break;
                case 3:member.amount();//for billing, amount function in member class has been calleda
                    break;
                case 4:quit=1;
                    break;

            }
        }
        sc.close();
    }

}

class Book {
    int[] book_id=new int[50];
    int[] quantity=new int[50];
}

class Date {
    //store individual member date of issue is they borrowed book
    int[] dd=new int[50];
    int[] mm=new int[50];
    int[] yyyy=new int[50];
    public void issue(int dd,int mm,int yyyy,int i)
    {
        this.dd[i]=dd;
        this.mm[i]=mm;
        this.yyyy[i]=yyyy;
    }
}

class Member {
    int[] memberID=new int[50];
    int[] bill =new int[50];
    int[] id=new int[50];//id of book issued to particular member;
    Scanner sc=new Scanner(System.in);



    public void issue(Book book,Date date)
    {
        System.out.print("Enter Member Id: ");
        int member_id= sc.nextInt();
        int i=0;
        while(member_id!=memberID[i])
        {
            if(i<51)
                i++;//when it exists, we have the i which will refer to the current member at the counter
        }
        if(member_id==memberID[i])
        {

                System.out.print("Enter Book Id: ");
                int book_id=sc.nextInt();


                int j=0;
                while(book.book_id[j]!=book_id)
                {
                    if(j<51)
                        j++;//same as member, here we will have the current book id
                }
                if(book.book_id[j]==book_id)
                {
                    if(book.quantity[j]!=0)
                    {
                        this.id[i]=book.book_id[j];
                        int dd,mm,yyyy;
                        System.out.print("\nEnter issue date[dd]: ");
                        dd=sc.nextInt();
                        System.out.print("\nEnter issue month[mm]: ");
                        mm=sc.nextInt();
                        System.out.print("\nEnter issue year[yyyy]: ");
                        yyyy=sc.nextInt();
                        date.issue(dd,mm,yyyy,i);
                        book.quantity[j]=book.quantity[j]-1;//when book has been borrowed, total quantity will decrease by 1
                        System.out.println("Book borrowed successfully! Remember to submit it within the next 7 days.");
                    }
                    else
                    {
                        System.out.println("No such book available at the moment. Please try again later.");
                    }
                }
                else
                {
                    System.out.println("Error: No such book exists");
                    return;
                }
        }
        else
        {
            System.out.println("Error: Member does not exist!");
        }
    }
    FetchBill fetchBill=new FetchBill();
    public void rtn(Book book,Date date)
    {
        System.out.print("\nEnter member id: ");
        int member_id=sc.nextInt();
        int k=0;
        while(memberID[k]!=member_id)
        {
            k++;
        }
        if(memberID[k]==member_id)//now while returning we also need to check the number of dats and the bill= sum
        {
                int sum=fetchBill.fetch(book,date,k);
                book.quantity[k]+=1;
                bill[k]=bill[k]+sum;
        }
        else
        {
            System.out.println("Error: Member does not exist!");
        }

    }
    public void amount()
    {

        System.out.print("\nEnter Member Id: ");
        int member_id= sc.nextInt();
        int i=0;
        while(member_id!=memberID[i]) {
            i++;
        }
        if(member_id==memberID[i])
        {
            System.out.print("\nAmount due: "+ bill[i]);//bill related to that particular index related to member we are talking about here

        }
        else
        {
            System.out.println("Error: Id doesn't exist!");
        }
    }

}

class FetchBill {
    int dd,mm,yyyy;
    private int sum = 0;//initially

    Scanner sc=new Scanner(System.in);


    public int fetch(Book book,Date date,int k)
    {
        System.out.println("Enter date of return(dd): ");
        dd=sc.nextInt();
        System.out.println("Enter month of return(mm): ");
        mm=sc.nextInt();
        System.out.println("Enter year of return(yyyy): ");
        yyyy=sc.nextInt();

        int no_month;
        int no_days;
        int remdaysten;//days on which fine is 10 rs per day
        int remdaysfifty;//days on which fine is 50 rs per day
        int remdays;//days on which fine has to be taken from member

        //assuming that user returns the book in same year,i.e  yyyy==date.yyyy[k]
        no_month=mm-date.mm[k];//number of months = returning month minus issuing month

        if(mm==date.mm[k])//returned in same month
        {
            no_days=dd-date.dd[k];//number of days will be shown
        }
        else
        {
            no_days=(30-date.dd[k])+dd+ (no_month-1)*30;//assuming each month has 30 days
        }
        if(no_days>7)
        {
            remdays=no_days-7;

            if (remdays>30){
                remdaysten=30;
                remdaysfifty=remdays-30;
            }
            else
            {
                remdaysten=remdays;
                remdaysfifty=0;
            }
            sum=remdaysten*10+remdaysfifty*50;
        }
        else
        {
            sum=0;
        }
        return sum;
    }
}
