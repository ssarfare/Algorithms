import random
from matplotlib import pyplot as plt
from datetime import datetime


data=[]    # defining list for storing the final data for every run.

size=[1000,2000,4000,5000,8000,10000]#,20000,30000,40000,50000];  # defining the size of various input


def InsertionSort(a):
    for i in range(1,len(a)):
        key=a[i];
        j=i-1;
        while j>=0 and key<a[j]:
            a[j+1]=a[j];
            j=j-1;
        a[j+1]=key;
    return a


                    
for j in size:
    avgtime=0;
    print('running for size-'+str(j))
    for i in range(3):
        unsortedArray=[];
        random.seed(i)    # used to produced same input arrays when running various algorithms
        for i in range(j): # use range(j,0,-1) for inversing the array
            unsortedArray.append(random.randint(0,j))  # generates random number
            #unsortedArray.append(i)   #for generating array of sorted sequence from i to j
        starttime=datetime.now()
        result=InsertionSort(unsortedArray)
        endtime=datetime.now()
        avgtime=avgtime+(endtime-starttime).total_seconds();
    data.append(round(avgtime/3,3));    # takes average of 3 runs for different input arrays

print(data); # prints the execution time for all arrys of differen input size

a=range(len(size))
plt.plot(a,data);
plt.xlabel('Array input size -->');
plt.ylabel('Execution time (seconds) -->');
plt.grid();
plt.title('Running time for Insertion Sort')
plt.xticks(a,size)
plt.show();


