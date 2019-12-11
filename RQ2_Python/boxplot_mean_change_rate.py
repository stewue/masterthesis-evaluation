import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def convert(data):
    convered = data.to_numpy()
    return convered[np.logical_not(np.isnan(convered))]

data = pd.read_csv('D:\\test.csv', delimiter=";")

labels = []
labelIndex = []
array = []

for column in data.columns:
    labels.append(column)
    labelIndex.append(len(labels))
    array.append(convert(data[column]))

plt.boxplot(array)
plt.ylim(-0.001,0.1)
plt.ylabel("mean change rate")
plt.xticks(labelIndex, labels, rotation='vertical')
plt.tight_layout()
plt.show()