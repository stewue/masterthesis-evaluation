import pandas as pd
import numpy as np
from scipy import stats

data = pd.read_csv('D:\\mp\\singleshot-fork.csv')

values = data[np.logical_not(np.isnan(data["modeIsSingleShotTime"]))][np.logical_not(np.isnan(data["forks"]))]

print(stats.pointbiserialr(values['modeIsSingleShotTime'], values['forks']).correlation.round(2))
print(stats.pointbiserialr(values['modeIsSingleShotTime'], values['forks']).pvalue)