import random
from matplotlib import pyplot as plt
from datetime import datetime



data=[]
size=[1000,2000,4000,5000,8000,10000,20000,30000,40000,50000];

def Merge(a,b,S):
    la=len(a);
    lb=len(b);
    i=j=0;
    S=[];
    while(i<la and j<lb):
        if(a[i]<=b[j]):
            S.append(a[i]);
            i=i+1;
        else:
            S.append(b[j]);
            j=j+1;
    while(i<la):
        S.append(a[i]);
        i=i+1;
    while(j<lb):
        S.append(b[j]);
        j=j+1;
    return S;


def MergeSort(a):
    n=len(a);
    if(n==1):
        return a;
    else:
        mid=n//2;
        left=a[:mid];
        right=a[mid:];
        left=MergeSort(left);
        right=MergeSort(right);
        return Merge(left,right,a);


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
        result=MergeSort(unsortedArray)
        endtime=datetime.now()
        avgtime=avgtime+(endtime-starttime).total_seconds();
    data.append(round(avgtime/3,3));   # takes average of 3 runs for different input arrays

print(data);

a=range(len(size))
plt.plot(a,data);
plt.xlabel('Array input size -->');
plt.ylabel('Execution time (seconds) -->');
plt.grid();
plt.title('Running time for Merge Sort (Inversely sorted input array)')
plt.xticks(a,size)
plt.show();


    
