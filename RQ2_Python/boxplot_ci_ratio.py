import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def convert(data):
    convered = data.to_numpy()
    return convered[np.logical_not(np.isnan(convered))]

type = 'divergence'
dataMean = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_mean.csv', delimiter=";")
dataMin = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_min.csv', delimiter=";")
dataMax = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_max.csv', delimiter=";")

mean = []
min = []
max = []

for column in dataMean.columns:
    mean.append(convert(dataMean[column]))
for column in dataMin.columns:
    min.append(convert(dataMin[column]))
for column in dataMax.columns:
    max.append(convert(dataMax[column]))

values = [
    np.concatenate(min, axis=0),
    np.concatenate(mean, axis=0),
    np.concatenate(max, axis=0)
]

plt.boxplot(values)
plt.ylabel("CI ratio")
plt.xticks([1,2,3], ['min', 'mean', 'max'])
plt.ylim(-0.005, 0.125)
plt.tight_layout()
# plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\boxplot_ciratio_' + type + '.pdf')