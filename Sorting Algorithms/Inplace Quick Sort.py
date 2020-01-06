# Quick  Sort, we choose Pivot to always be the last element.
import sys
import random
from matplotlib import pyplot as plt

from datetime import datetime
sys.setrecursionlimit(100000)

data=[]    # defining list for storing the final data for every run.

size=[1000,2000,4000,5000,8000]#,10000,20000,30000,40000,50000];  # defining the size of various input

def InsertionSort(a,startIndex,endIndex):
    for i in range(startIndex+1,endIndex+1):
        key=a[i];
        j=i-1;
        while j>=0 and key<a[j]:
            a[j+1]=a[j];
            j=j-1;
        a[j+1]=key;
    return a;


def partition(a,startIndex,endIndex,pI):
    a[startIndex],a[pI]=a[pI],a[startIndex];
    pivot=a[startIndex];
    i=j=startIndex+1;
    while j<=endIndex:
        if a[j]<=pivot:
            a[j],a[i]=a[i],a[j];
            i=i+1;
        j=j+1;
    a[startIndex],a[i-1]=a[i-1],a[startIndex];
    return i-1;

    
def QuickSort(a,startIndex,endIndex):
    if(not(startIndex+endIndex<=10)) :
        if(startIndex<endIndex):
            index=random.randint(startIndex,endIndex);
            pivotIndex=index;
            pIndex=partition(a,startIndex,endIndex,pivotIndex);
            QuickSort(a,startIndex,pIndex-1);
            QuickSort(a,pIndex+1,endIndex);
    else:
        InsertionSort(a,startIndex,endIndex);
        


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
        QuickSort(unsortedArray,0,len(unsortedArray)-1)        
        endtime=datetime.now()
        avgtime=avgtime+(endtime-starttime).total_seconds();
    data.append(round(avgtime/3,3)); # takes average of 3 runs for different input arrays

print(data);

a=range(len(size))
plt.plot(a,data);
plt.xlabel('Array input size -->');
plt.ylabel('Execution time (seconds) -->');
plt.grid();
plt.title('Running time for Inplace Quick Sort')
plt.xticks(a,size)
plt.show();


