import pandas as pd
import numpy as np
from scipy import stats

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

values = data[np.logical_not(np.isnan(data["forks"]))]
values['modeIsSingleShotTime'] = values['modeIsSingleShotTime'].fillna(False)

print(stats.pointbiserialr(values['modeIsSingleShotTime'], values['forks']).correlation.round(2))
print(stats.pointbiserialr(values['modeIsSingleShotTime'], values['forks']).pvalue)