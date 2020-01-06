import random
from matplotlib import pyplot as plt
from datetime import datetime


data=[]
size=[1000,2000,4000,5000,8000,10000,20000,30000,40000,50000];


#Vector based Heap Sort
heap=[]; # Zeroth element
def insertItem(heap,i):
    heap.append(i);
    heapSize=len(heap);
    i=heapSize-1;
    while (i>1 and heap[i//2]>heap[i]):
        heap[i//2],heap[i]=heap[i],heap[i//2];
        i=i//2;


def removeItem(heap):
    if(len(heap)>1):
        temp=heap[1];
        n=len(heap)-1;
        heap[1]=heap[n];
        n=n-1;
        i=1;
        while i<n:
            if 2*i+1<=n:
                if heap[i]<=heap[2*i] and heap[i]<=heap[2*i+1]:
                    return temp;
                else:
                    j=None
                    if heap[2*i]<=heap[2*i+1]:
                        j=2*i;
                    else:
                        j=2*i+1;
                    heap[i],heap[j]=heap[j],heap[i];
                    i=j;
            else:
                if 2*i<=n:
                    if heap[i]<heap[2*i]:
                        heap[i],heap[2*i]=heap[2*i],heap[i];
                return temp
        return temp;

for j in size:
    avgtime=0;
    print('running for size-'+str(j))
    for i in range(3):
        unsortedArray=[];
        heap=[None];
        random.seed(i)    # used to produced same input arrays when running various algorithms
        for i in range(j): # use range(j,0,-1) for inversing the array
            unsortedArray.append(random.randint(0,j))  # generates random number
            #unsortedArray.append(i)   #for generating array of sorted sequence from i to j
        starttime=datetime.now()
        for i in unsortedArray:
            insertItem(heap,i); #insert items to heap one element at a time
        result=[];
        for i in range(1,len(heap)):
            result.append(removeItem(heap)); #removing items from heap one at a time and storing the sorted array in the result
        endtime=datetime.now()
        avgtime=avgtime+(endtime-starttime).total_seconds();
    data.append(round(avgtime/3,3));


print(data);

a=range(len(size))
plt.plot(a,data);
plt.xlabel('Array input size -->');
plt.ylabel('Execution time (seconds) -->');
plt.grid();
plt.title('Running time for Heap Sort (Inversely sorted input array)')
plt.xticks(a,size)
plt.show();

