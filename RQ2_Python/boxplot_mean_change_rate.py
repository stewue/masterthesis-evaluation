import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def convert(data):
    convered = data.to_numpy()
    return convered[np.logical_not(np.isnan(convered))]

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotmeanchangerate.csv', delimiter=";")

labels = []
labelIndex = []
array = []

for column in data.columns:
    labels.append(column)
    labelIndex.append(len(labels))
    array.append(convert(data[column]))

all = np.concatenate( array, axis=0 )
labels.append('all')
labelIndex.append(len(labels))
array.append(all)

plt.boxplot(array)
plt.ylim(-0.001,0.1)
plt.ylabel("mean change rate")
plt.xticks(labelIndex, labels, rotation='vertical')
plt.tight_layout()
# plt.show()
plt.savefig('export.png')

print("all median: " + str(np.median(all)))
filtered = all[all < 0.02]
print("change smaller 2%:  " + str(len(filtered)/len(all)))