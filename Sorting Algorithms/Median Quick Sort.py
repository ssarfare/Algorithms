#Median Quick Sort
import sys
import random
from matplotlib import pyplot as plt

from datetime import datetime
sys.setrecursionlimit(100000)


data=[]
size=[20]#,2000]#,4000,5000,8000,10000,20000,30000,40000,50000];


def InsertionSort(a,startIndex,endIndex):
    for i in range(startIndex,endIndex+1):
        key=a[i];
        j=i-1;
        while j>=0 and key<a[j]:
            a[j+1]=a[j];
            j=j-1;
        a[j+1]=key;
    return a;

'''def partition(arr, low, high):
    pivotindex=get_pivot(arr,low,high)
    pivotvalue=arr[pivotindex]
    arr[pivotindex],arr[low]=arr[low],arr[pivotindex] #swapping the first element and the pivot
    border=low;

    for i in range(low,high+1):
        if arr[i]<pivotvalue:
            border=border+1
            arr[i],arr[border]=arr[border],arr[i]   #swapping values which is less than pivot with the border value
            
    arr[low],arr[border]=arr[border],arr[low]  #swapping border value and the pivot value at the end of the iteration
    return border'''
def partition(a,startIndex,endIndex,pivotindex):

    pivotvalue=a[pivotindex]
    a[startIndex],a[pivotindex]=a[pivotindex],a[startIndex];
    i=j=startIndex+1;
    while j<=endIndex:
        if a[j]<=pivotvalue:
            a[j],a[i]=a[i],a[j];
            i=i+1;
        j=j+1;
    a[startIndex],a[i-1]=a[i-1],a[startIndex];
    return i-1;
        

def getMedian(a,startIndex,endIndex):    #median of three(low,high,mid) as pivot
    mid=(startIndex+endIndex)//2;
    if(a[mid]<a[startIndex]):
        a[mid],a[startIndex]=a[startIndex],a[mid];
    if(a[endIndex]<a[startIndex]):
        a[startIndex],a[endIndex]=a[endIndex],a[startIndex];
    if(a[endIndex]<a[mid]):
        a[mid],a[endIndex]=a[endIndex],a[mid];

    a[mid],a[endIndex-1]=a[endIndex-1],a[mid];
    return a[endIndex-1];


def medianQuickSort(a,startIndex,endIndex):
    if(startIndex+10<=endIndex):
        pivot=getMedian(a,startIndex,endIndex)
        i=startIndex;
        j=endIndex-2
        
        while True:
            while(a[i]<pivot):
                i=i+1;
            while(a[j]>pivot):
                j=j-1;
            if(i<j):
                a[i],a[j]=a[j],a[i];
            else:
                break;
        a[i],a[endIndex-1]=a[endIndex-1],a[i];
        medianQuickSort(a,startIndex,i-1);
        medianQuickSort(a,i+1,endIndex);
    else:
        InsertionSort(a,startIndex,endIndex);
        
for j in size:
    avgtime=0;
    print('running for size-'+str(j))
    for i in range(3):
        unsortedArray=[];
        random.seed(i)
        for i in range(j):
            unsortedArray.append(random.randint(0,j))
            #unsortedArray.append(i)
        #print(unsortedArray)
        starttime=datetime.now()
        medianQuickSort(unsortedArray,0,len(unsortedArray)-1);
        endtime=datetime.now()
        #print(unsortedArray)
        avgtime=avgtime+(endtime-starttime).total_seconds();
    data.append(round(avgtime/3,3));

print(data);

a=range(len(size))
plt.plot(a,data);
plt.xlabel('Array input size -->');
plt.ylabel('Execution time (seconds) -->');
plt.grid();
plt.title('Running time for Median Quick Sort (Input array already sorted)')
plt.xticks(a,size)
plt.show();

