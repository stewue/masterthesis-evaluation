import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def convert(data):
    convered = data.to_numpy()
    return convered[np.logical_not(np.isnan(convered))]

type = 'divergence'
data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplot_meanchangerate_' + type + '.csv', delimiter=";")

labels = []
labelIndex = []
array = []

for column in data.columns:
    labels.append(column)
    labelIndex.append(len(labels))
    array.append(convert(data[column]))

all = np.concatenate(array, axis=0)
labels.append('all')
labelIndex.append(len(labels))
array.append(all)

plt.boxplot(array)
plt.ylim(-0.001, 0.125)
plt.ylabel("change rate")
plt.xticks(labelIndex, labels, rotation='vertical')
plt.tight_layout()
# plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\boxplot_meanchangerate_' + type + '.pdf')

print("all median: " + str(np.median(all)))
filtered = all[all < 0.02]
print("change smaller 2%:  " + str(len(filtered) / len(all)))