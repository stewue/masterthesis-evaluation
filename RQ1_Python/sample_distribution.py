import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\history-selected-commits.csv')

counts = data.groupby('project').count()['commitId']

plt.hist(counts, range=[0,65], bins=13)
plt.ylabel('# projects')
plt.xlabel('# sample commits')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')

print("max: " + str(counts.max()))
print("avg: " + str(np.average(counts.values).round(1)))
print("median: " + str(np.median(counts.values)))