import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv',dtype='str')

filter1 = data[data['mainRepo'] == 'true']
numberOf = filter1['numberOfBenchmarks'].astype(int)
filter2 = numberOf[numberOf > 0]

all, base = np.histogram(filter2, bins=[0,5,10,25,50,75,100,200,600])

label = ('1-4', '5-9', '10-24', '25-49', '50-74', '75-99', '100-199', '200-515')
x = np.arange(8)

plt.bar(x, all)
plt.xticks(x, label)
plt.ylabel('# projects')
plt.xlabel('# benchmarks')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')